package com.busbooking.service;

import com.busbooking.dto.BookingRequestDTO;
import com.busbooking.dto.BookingResponseDTO;
import com.busbooking.dto.BookingDetailsResponseDTO;
import com.busbooking.dto.TransactionHistoryDTO;
import com.busbooking.enums.BookingStatus;
import com.busbooking.enums.SeatStatus;
import com.busbooking.enums.TicketStatus;
import com.busbooking.exception.BookingCancelledException;
import com.busbooking.exception.BookingNotFoundException;
import com.busbooking.exception.SeatBookedException;
import com.busbooking.exception.SeatNotFoundException;
import com.busbooking.exception.TripNotFoundException;
import com.busbooking.exception.ValidationException;
import com.busbooking.model.Booking;
import com.busbooking.model.BusTrip;
import com.busbooking.model.Customer;
import com.busbooking.model.Seat;
import com.busbooking.model.Ticket;
import com.busbooking.repository.BookingRepository;
import com.busbooking.repository.CustomerRepository;
import com.busbooking.repository.SeatRepository;
import com.busbooking.repository.TicketRepository;
import com.busbooking.repository.TripRepository;
import com.busbooking.repository.PaymentRepository;
import com.busbooking.utils.IdGenerator;
import com.busbooking.utils.PriceCalculator;
import com.busbooking.utils.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final Map<com.busbooking.enums.CustomerType, DiscountPolicy> discountPolicies;

    public BookingService(TripRepository tripRepository, SeatRepository seatRepository,
            CustomerRepository customerRepository, BookingRepository bookingRepository,
            TicketRepository ticketRepository, PaymentRepository paymentRepository,
            List<DiscountPolicy> discountPolicies) {
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
        this.discountPolicies = discountPolicies.stream()
                .collect(java.util.stream.Collectors.toMap(DiscountPolicy::getCustomerType, Function.identity()));
    }

    public BookingResponseDTO bookTicket(BookingRequestDTO request) {
        if (request == null)
            throw new ValidationException("Booking request is required");
        BusTrip trip = validateTrip(request.getTripId());
        Seat seat = validateSeat(request.getTripId(), request.getSeatNumber());
        Customer customer = validateCustomer(request);
        BigDecimal total = calculateTotalPrice(trip, seat, customer);
        String bookingId = generateBookingId();
        Ticket ticket = generateTicket(bookingId, total);
        Booking booking = new Booking(bookingId, customer.getId(), trip.getId(), seat.getSeatNumber(), ticket.getId(),
                total, BookingStatus.PENDING, LocalDateTime.now());
        bookingRepository.save(booking);
        updateSeatStatus(seat);
        return toResponse(booking, customer.getFullName());
    }

    public BookingResponseDTO cancelBooking(String bookingId) {
        Booking booking = requireBooking(bookingId);
        if (booking.getStatus() == BookingStatus.CANCELLED)
            throw new BookingCancelledException("Vé đã được hủy trước đó.");
        BusTrip trip = tripRepository.findById(booking.getTripId())
                .orElseThrow(() -> new TripNotFoundException(booking.getTripId()));
        if (!LocalDateTime.of(trip.getDepartureDate(), trip.getDepartureTime()).isAfter(LocalDateTime.now()))
            throw new ValidationException("Không thể hủy vì chuyến xe đã khởi hành.");
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        bookingRepository.update(booking);
        ticketRepository.findById(booking.getTicketId()).ifPresent(ticket -> {
            ticket.setStatus(TicketStatus.CANCELLED);
            ticketRepository.update(ticket);
        });
        releaseSeat(booking.getTripId(), booking.getSeatNumber());
        return toResponse(booking,
                customerRepository.findById(booking.getCustomerId()).map(Customer::getFullName).orElse(""));
    }

    public BookingResponseDTO findBooking(String bookingId) {
        Booking booking = requireBooking(bookingId);
        return toResponse(booking,
                customerRepository.findById(booking.getCustomerId()).map(Customer::getFullName).orElse(""));
    }

    public BookingDetailsResponseDTO findBookingDetails(String bookingId, String phone) {
        if (phone == null || !phone.matches("\\d{10}"))
            throw new ValidationException("A valid 10-digit phone number is required");
        Booking booking = requireBooking(bookingId);
        Customer customer = customerRepository.findById(booking.getCustomerId())
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        if (!customer.getPhone().equals(phone))
            throw new BookingNotFoundException(bookingId);
        return toDetails(booking, customer);
    }

    public List<BookingDetailsResponseDTO> findBookingDetailsByPhone(String phone) {
        return customerRepository.findAll().stream().filter(customer -> customer.getPhone().equals(phone)).findFirst()
                .map(customer -> bookingRepository.findByCustomer(customer.getId()).stream()
                        .map(booking -> toDetails(booking, customer)).toList())
                .orElse(List.of());
    }

    public List<BookingDetailsResponseDTO> findAllBookingDetails(String keyword, BookingStatus bookingStatus,
            com.busbooking.enums.PaymentStatus paymentStatus, String busCompany, java.time.LocalDate departureDate) {
        return bookingRepository.findAll().stream().map(booking -> {
            Customer customer = customerRepository.findById(booking.getCustomerId()).orElse(null);
            return customer == null ? null : toDetails(booking, customer);
        }).filter(java.util.Objects::nonNull)
                .filter(details -> matches(details, keyword, bookingStatus, paymentStatus, busCompany, departureDate))
                .toList();
    }

    public List<BookingResponseDTO> findBookingHistory(String customerId) {
        return bookingRepository.findByCustomer(customerId).stream().map(booking -> toResponse(booking,
                customerRepository.findById(customerId).map(Customer::getFullName).orElse(""))).toList();
    }

    public List<BookingResponseDTO> findBookingHistoryByPhone(String phone) {
        return customerRepository.findAll().stream().filter(customer -> customer.getPhone().equals(phone)).findFirst()
                .map(customer -> findBookingHistory(customer.getId())).orElse(List.of());
    }

    public boolean belongsToCustomer(String bookingId, String phone) {
        Booking booking = requireBooking(bookingId);
        return customerRepository.findById(booking.getCustomerId()).map(customer -> customer.getPhone().equals(phone))
                .orElse(false);
    }

    public boolean customerIdBelongsToPhone(String customerId, String phone) {
        return customerRepository.findById(customerId).map(customer -> customer.getPhone().equals(phone)).orElse(false);
    }

    public BusTrip validateTrip(String tripId) {
        Validator.validateTripId(tripId);
        BusTrip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        if (LocalDateTime.of(trip.getDepartureDate(), trip.getDepartureTime()).isBefore(LocalDateTime.now()))
            throw new ValidationException("Trip departure time has passed");
        return trip;
    }

    public Seat validateSeat(String tripId, String seatNumber) {
        Validator.validateSeatNumber(seatNumber);
        Seat seat = seatRepository.findSeat(tripId, seatNumber)
                .orElseThrow(() -> new SeatNotFoundException(tripId, seatNumber));
        if (seat.getStatus() != SeatStatus.AVAILABLE)
            throw new SeatBookedException(seatNumber);
        return seat;
    }

    public Customer validateCustomer(BookingRequestDTO request) {
        Validator.validateCustomer(request.getCustomerName(), request.getPhone(), request.getEmail(),
                request.getCustomerType());
        return customerRepository.findAll().stream().filter(customer -> customer.getPhone().equals(request.getPhone()))
                .findFirst()
                .orElseGet(() -> customerRepository.save(new Customer(IdGenerator.generateCustomerId(),
                        request.getCustomerName().trim(), request.getPhone(), request.getEmail().trim(),
                        request.getPhone(), request.getCustomerType())));
    }

    public BigDecimal calculateTotalPrice(BusTrip trip, Seat seat, Customer customer) {
        BigDecimal subtotal = PriceCalculator.calculateBasePrice(trip.getBasePrice(), seat.getSeatType());
        BigDecimal finalPrice = subtotal.subtract(applyDiscount(subtotal, customer));
        if (finalPrice.signum() <= 0)
            throw new ValidationException("Final price must be greater than zero");
        return finalPrice;
    }

    public BigDecimal applyDiscount(BigDecimal amount, Customer customer) {
        return discountPolicies.get(customer.getCustomerType()).calculateDiscount(amount);
    }

    public String generateBookingId() {
        return IdGenerator.generateBookingId();
    }

    public Ticket generateTicket(String bookingId, BigDecimal finalPrice) {
        Ticket ticket = new Ticket(IdGenerator.generateTicketId(), bookingId, finalPrice, TicketStatus.UNPAID,
                LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public void updateSeatStatus(Seat seat) {
        seat.setStatus(SeatStatus.BOOKED);
        seatRepository.updateSeat(seat);
    }

    public void releaseSeat(String tripId, String seatNumber) {
        Seat seat = seatRepository.findSeat(tripId, seatNumber)
                .orElseThrow(() -> new SeatNotFoundException(tripId, seatNumber));
        seat.setStatus(SeatStatus.AVAILABLE);
        seatRepository.updateSeat(seat);
    }

    private Booking requireBooking(String bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    private BookingResponseDTO toResponse(Booking booking, String customerName) {
        return new BookingResponseDTO(booking.getId(), booking.getTicketId(), booking.getSeatNumber(),
                booking.getTotalPrice(), booking.getStatus(), customerName);
    }

    private BookingDetailsResponseDTO toDetails(Booking booking, Customer customer) {
        BusTrip trip = tripRepository.findById(booking.getTripId())
                .orElseThrow(() -> new TripNotFoundException(booking.getTripId()));
        Seat seat = seatRepository.findSeat(booking.getTripId(), booking.getSeatNumber())
                .orElseThrow(() -> new SeatNotFoundException(booking.getTripId(), booking.getSeatNumber()));
        Ticket ticket = ticketRepository.findById(booking.getTicketId())
                .orElseThrow(() -> new BookingNotFoundException(booking.getId()));
        var payment = paymentRepository.findByBookingId(booking.getId()).orElse(null);
        BigDecimal subtotal = PriceCalculator.calculateBasePrice(trip.getBasePrice(), seat.getSeatType());
        BigDecimal surcharge = subtotal.subtract(trip.getBasePrice());
        BigDecimal discount = PriceCalculator.calculateDiscount(subtotal, customer.getCustomerType());
        List<TransactionHistoryDTO> history = new ArrayList<>();
        history.add(new TransactionHistoryDTO(booking.getCreatedAt(), "BOOKING_CREATED", "Khách hàng đặt vé."));
        if (payment != null && payment.getStatus() == com.busbooking.enums.PaymentStatus.SUCCESS) {
            history.add(
                    new TransactionHistoryDTO(payment.getPaymentTime(), "PAYMENT_SUCCESS", "Thanh toán thành công."));
            history.add(new TransactionHistoryDTO(payment.getPaymentTime(), "BOOKING_CONFIRMED",
                    "Booking đã được xác nhận."));
        }
        if (booking.getCancelledAt() != null)
            history.add(new TransactionHistoryDTO(booking.getCancelledAt(), "BOOKING_CANCELLED", "Vé đã được hủy."));
        return new BookingDetailsResponseDTO(booking.getId(), ticket.getId(), booking.getStatus(), ticket.getStatus(),
                booking.getCreatedAt(), customer.getFullName(), customer.getPhone(), customer.getEmail(),
                customer.getCustomerType(), trip.getId(), trip.getBusCompany(), trip.getBusType(), trip.getDeparture(),
                trip.getDestination(), trip.getDepartureDate(), trip.getDepartureTime(), trip.getArrivalTime(),
                seat.getSeatNumber(), seat.getSeatType(), seat.getStatus(), trip.getBasePrice(), surcharge, discount,
                booking.getTotalPrice(), payment == null ? null : payment.getId(),
                payment == null ? null : payment.getPaymentMethod(), payment == null ? null : payment.getStatus(),
                payment == null ? null : payment.getPaymentTime(), history);
    }

    private boolean matches(BookingDetailsResponseDTO details, String keyword, BookingStatus bookingStatus,
            com.busbooking.enums.PaymentStatus paymentStatus, String busCompany, java.time.LocalDate departureDate) {
        String normalized = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean keywordMatches = normalized.isBlank() || details.bookingId().toLowerCase().contains(normalized)
                || details.customerName().toLowerCase().contains(normalized) || details.phone().contains(normalized)
                || (details.departure() + " " + details.destination()).toLowerCase().contains(normalized);
        return keywordMatches && (bookingStatus == null || details.bookingStatus() == bookingStatus)
                && (paymentStatus == null || details.paymentStatus() == paymentStatus)
                && (busCompany == null || busCompany.isBlank()
                        || details.busCompany().equalsIgnoreCase(busCompany.trim()))
                && (departureDate == null || departureDate.equals(details.departureDate()));
    }
}
