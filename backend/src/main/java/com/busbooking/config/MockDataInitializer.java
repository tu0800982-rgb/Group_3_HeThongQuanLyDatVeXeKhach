package com.busbooking.config;

import com.busbooking.enums.BookingStatus;
import com.busbooking.enums.CustomerType;
import com.busbooking.enums.PaymentMethodType;
import com.busbooking.enums.PaymentStatus;
import com.busbooking.enums.SeatStatus;
import com.busbooking.enums.SeatType;
import com.busbooking.enums.TicketStatus;
import com.busbooking.enums.UserRole;
import com.busbooking.model.Booking;
import com.busbooking.model.BusTrip;
import com.busbooking.model.Customer;
import com.busbooking.model.Payment;
import com.busbooking.model.Seat;
import com.busbooking.model.Ticket;
import com.busbooking.model.User;
import com.busbooking.repository.BookingRepository;
import com.busbooking.repository.CustomerRepository;
import com.busbooking.repository.PaymentRepository;
import com.busbooking.repository.SeatRepository;
import com.busbooking.repository.TicketRepository;
import com.busbooking.repository.TripRepository;
import com.busbooking.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MockDataInitializer implements CommandLineRunner {
    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public MockDataInitializer(TripRepository tripRepository, SeatRepository seatRepository,
            CustomerRepository customerRepository, BookingRepository bookingRepository,
            TicketRepository ticketRepository, PaymentRepository paymentRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... arguments) {
        clearAll();
        createAdmin();
        createTripsAndSeats();
        createCustomers();
        createBookingsTicketsAndPayments();
    }

    private void clearAll() {
        tripRepository.clear();
        seatRepository.clear();
        customerRepository.clear();
        bookingRepository.clear();
        ticketRepository.clear();
        paymentRepository.clear();
        userRepository.clear();
    }

    private void createAdmin() {
        userRepository.save(new User("USR-ADMIN", "admin", "0987654321", "", "admin", UserRole.ADMIN));
    }

    private void createTripsAndSeats() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        List<BusTrip> trips = List.of(
                new BusTrip("TRP-001", "Hai Phong Express", "Limousine 45", "Ha Noi", "Hai Phong", startDate,
                        LocalTime.of(7, 0), LocalTime.of(9, 30), new BigDecimal("150000")),
                new BusTrip("TRP-002", "Green Transit", "Sleeper 45", "Ha Noi", "Hai Phong", startDate,
                        LocalTime.of(9, 0), LocalTime.of(11, 30), new BigDecimal("160000")),
                new BusTrip("TRP-003", "Coastal Bus", "Limousine 45", "Ha Noi", "Hai Phong", startDate.plusDays(1),
                        LocalTime.of(13, 0), LocalTime.of(15, 30), new BigDecimal("170000")),
                new BusTrip("TRP-004", "Hai Phong Express", "Limousine 45", "Hai Phong", "Ha Noi", startDate,
                        LocalTime.of(8, 0), LocalTime.of(10, 30), new BigDecimal("150000")),
                new BusTrip("TRP-005", "Green Transit", "Sleeper 45", "Hai Phong", "Ha Noi", startDate.plusDays(1),
                        LocalTime.of(10, 0), LocalTime.of(12, 30), new BigDecimal("160000")),
                new BusTrip("TRP-006", "Coastal Bus", "Limousine 45", "Hai Phong", "Ha Noi", startDate.plusDays(2),
                        LocalTime.of(17, 0), LocalTime.of(19, 30), new BigDecimal("170000")));
        trips.forEach(tripRepository::save);
        for (BusTrip trip : trips)
            createSeats(trip.getId());
    }

    private void createSeats(String tripId) {
        String[] rows = { "A", "B", "C", "D", "E" };
        for (String row : rows)
            for (int number = 1; number <= 8; number++)
                saveSeat(tripId, row + number);
        for (int number = 1; number <= 5; number++)
            saveSeat(tripId, "F" + number);
    }

    private void saveSeat(String tripId, String seatNumber) {
        SeatType type = seatNumber.startsWith("A") ? SeatType.VIP : SeatType.NORMAL;
        seatRepository.save(new Seat(tripId + "-" + seatNumber, tripId, seatNumber, type, SeatStatus.AVAILABLE));
    }

    private void createCustomers() {
        for (int index = 1; index <= 20; index++) {
            CustomerType type = index % 5 == 0 ? CustomerType.VIP
                    : index % 3 == 0 ? CustomerType.MEMBER : CustomerType.NORMAL;
            String number = String.format("%03d", index);
            customerRepository
                    .save(new Customer("CUS-" + number, "Customer " + number, "09" + String.format("%08d", index),
                            "customer" + index + "@example.com", "customer" + index, type));
        }
    }

    private void createBookingsTicketsAndPayments() {
        for (int index = 1; index <= 10; index++) {
            String number = String.format("%03d", index);
            String tripId = "TRP-" + String.format("%03d", ((index - 1) % 6) + 1);
            String seatNumber = index <= 8 ? "B" + index : "C" + (index - 8);
            String bookingId = "BKG-" + number;
            String ticketId = "TKT-" + number;
            boolean paid = index <= 5;
            BigDecimal price = new BigDecimal(index % 2 == 0 ? "160000" : "150000");
            Booking booking = new Booking(bookingId, "CUS-" + number, tripId, seatNumber, ticketId, price,
                    paid ? BookingStatus.CONFIRMED : BookingStatus.PENDING, LocalDateTime.now().minusDays(index % 3));
            bookingRepository.save(booking);
            ticketRepository.save(new Ticket(ticketId, bookingId, price, paid ? TicketStatus.PAID : TicketStatus.UNPAID,
                    booking.getCreatedAt()));
            seatRepository.findSeat(tripId, seatNumber).ifPresent(seat -> {
                seat.setStatus(SeatStatus.BOOKED);
                seatRepository.updateSeat(seat);
            });
            if (paid)
                paymentRepository.save(new Payment("PAY-" + number, bookingId, ticketId, price,
                        index % 2 == 0 ? PaymentMethodType.BANK_TRANSFER : PaymentMethodType.EWALLET,
                        PaymentStatus.SUCCESS, LocalDateTime.now().minusHours(index)));
        }
    }
}