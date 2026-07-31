package com.busbooking.dto;

import com.busbooking.enums.BookingStatus;
import com.busbooking.enums.CustomerType;
import com.busbooking.enums.PaymentMethodType;
import com.busbooking.enums.PaymentStatus;
import com.busbooking.enums.SeatType;
import com.busbooking.enums.SeatStatus;
import com.busbooking.enums.TicketStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record BookingDetailsResponseDTO(
                String bookingId, String ticketId, BookingStatus bookingStatus, TicketStatus ticketStatus,
                LocalDateTime bookingTime,
                String customerName, String phone, String email, CustomerType customerType,
                String tripId, String busCompany, String busType, String departure, String destination,
                LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime,
                String seatNumber, SeatType seatType, SeatStatus seatStatus, BigDecimal basePrice,
                BigDecimal vipSurcharge, BigDecimal discount, BigDecimal totalPrice,
                String paymentId, PaymentMethodType paymentMethod, PaymentStatus paymentStatus,
                LocalDateTime paymentTime,
                List<TransactionHistoryDTO> transactionHistory) {
}
