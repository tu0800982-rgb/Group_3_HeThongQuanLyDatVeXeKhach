package com.busbooking.service;

import com.busbooking.dto.DashboardResponseDTO;
import com.busbooking.dto.ReportResponseDTO;
import com.busbooking.enums.BookingStatus;
import com.busbooking.enums.CustomerType;
import com.busbooking.enums.PaymentStatus;
import com.busbooking.enums.SeatStatus;
import com.busbooking.enums.TicketStatus;
import com.busbooking.model.Payment;
import com.busbooking.repository.BookingRepository;
import com.busbooking.repository.CustomerRepository;
import com.busbooking.repository.PaymentRepository;
import com.busbooking.repository.SeatRepository;
import com.busbooking.repository.TicketRepository;
import com.busbooking.repository.TripRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;

    public DashboardService(BookingRepository bookingRepository, CustomerRepository customerRepository,
            PaymentRepository paymentRepository, SeatRepository seatRepository, TicketRepository ticketRepository,
            TripRepository tripRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
    }

    public DashboardResponseDTO getDashboard() {
        LocalDate today = LocalDate.now();
        return new DashboardResponseDTO(getDailyRevenue(today),
                bookingRepository.findAll().stream()
                        .filter(booking -> booking.getCreatedAt().toLocalDate().equals(today)).count(),
                getCancelledBookings(today), getTotalBookings(),
                bookingRepository.findAll().stream().filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED)
                        .count(),
                getSeatStatistics(SeatStatus.AVAILABLE), getSeatStatistics(SeatStatus.BOOKED),
                getCustomers(CustomerType.VIP), getCustomers(CustomerType.NORMAL),
                tripRepository.findAll().stream().filter(trip -> !trip.getDepartureDate().isBefore(today)).count(),
                tripRepository.findAll().stream().filter(trip -> trip.getDepartureDate().isBefore(today)).count());
    }

    public ReportResponseDTO getRevenue() {
        long availableSeats = getSeatStatistics(SeatStatus.AVAILABLE);
        return new ReportResponseDTO(
                successfulPayments().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add),
                tripRepository.count(), customerRepository.count(), getTotalBookings(), getCancelledBookings(),
                ticketRepository.findAll().stream().filter(ticket -> ticket.getStatus() == TicketStatus.PAID).count(),
                bookingRepository.findAll().stream()
                        .filter(booking -> customerRepository.findById(booking.getCustomerId())
                                .map(customer -> customer.getCustomerType() == CustomerType.VIP).orElse(false))
                        .count(),
                availableSeats, getSeatStatistics(SeatStatus.BOOKED), availableSeats);
    }

    public BigDecimal getDailyRevenue(LocalDate date) {
        return successfulPayments().filter(payment -> payment.getPaymentTime().toLocalDate().equals(date))
                .map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long getTotalBookings() {
        return bookingRepository.count();
    }

    public long getCancelledBookings() {
        return getCancelledBookings(null);
    }

    public long getVIPCustomers() {
        return getCustomers(CustomerType.VIP);
    }

    public long getSeatStatistics(SeatStatus status) {
        return seatRepository.findAll().stream().filter(seat -> seat.getStatus() == status).count();
    }

    private long getCancelledBookings(LocalDate date) {
        return bookingRepository.findAll().stream().filter(booking -> booking.getStatus() == BookingStatus.CANCELLED
                && (date == null || booking.getCreatedAt().toLocalDate().equals(date))).count();
    }

    private long getCustomers(CustomerType type) {
        return customerRepository.findAll().stream().filter(customer -> customer.getCustomerType() == type).count();
    }

    private java.util.stream.Stream<Payment> successfulPayments() {
        return paymentRepository.findAll().stream().filter(payment -> payment.getStatus() == PaymentStatus.SUCCESS)
                .filter(payment -> bookingRepository.findById(payment.getBookingId())
                        .map(booking -> booking.getStatus() != BookingStatus.CANCELLED).orElse(false));
    }
}