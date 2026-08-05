package com.busbooking.service;

import com.busbooking.dto.PaymentRequestDTO;
import com.busbooking.dto.PaymentResponseDTO;
import com.busbooking.enums.BookingStatus;
import com.busbooking.enums.PaymentStatus;
import com.busbooking.enums.TicketStatus;
import com.busbooking.exception.BookingCancelledException;
import com.busbooking.exception.BookingNotFoundException;
import com.busbooking.exception.PaymentException;
import com.busbooking.model.Booking;
import com.busbooking.model.Payment;
import com.busbooking.model.Ticket;
import com.busbooking.repository.BookingRepository;
import com.busbooking.repository.PaymentRepository;
import com.busbooking.repository.TicketRepository;
import com.busbooking.utils.IdGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final Map<com.busbooking.enums.PaymentMethodType, PaymentMethod> paymentMethods;

    public PaymentService(BookingRepository bookingRepository, TicketRepository ticketRepository,
            PaymentRepository paymentRepository, List<PaymentMethod> paymentMethods) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMethods = paymentMethods.stream()
                .collect(java.util.stream.Collectors.toMap(PaymentMethod::getType, Function.identity()));
    }

    public PaymentResponseDTO pay(PaymentRequestDTO request) {
        validatePayment(request);
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(request.getBookingId()));
        Ticket ticket = ticketRepository.findById(booking.getTicketId())
                .orElseThrow(() -> new PaymentException("Ticket not found for booking"));
        Payment payment = new Payment(generatePaymentId(), booking.getId(), ticket.getId(), ticket.getFinalPrice(),
                request.getPaymentMethod(), PaymentStatus.PENDING, LocalDateTime.now());
        PaymentMethod paymentMethod = paymentMethods.get(request.getPaymentMethod());
        if (paymentMethod == null)
            throw new PaymentException("Unsupported payment method");
        paymentMethod.process(payment);
        paymentRepository.save(payment);
        ticket.setStatus(TicketStatus.PAID);
        ticketRepository.update(ticket);
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.update(booking);
        return toResponse(payment);
    }

    public PaymentResponseDTO refund(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Payment not found: " + paymentId));
        if (payment.getStatus() != PaymentStatus.SUCCESS)
            throw new PaymentException("Only successful payments can be refunded");
        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.update(payment);
        ticketRepository.findById(payment.getTicketId()).ifPresent(ticket -> {
            ticket.setStatus(TicketStatus.UNPAID);
            ticketRepository.update(ticket);
        });
        return toResponse(payment);
    }

    public void validatePayment(PaymentRequestDTO request) {
        if (request == null || request.getBookingId() == null || request.getBookingId().isBlank()
                || request.getPaymentMethod() == null)
            throw new PaymentException("Booking ID and payment method are required");
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(request.getBookingId()));
        if (booking.getStatus() == BookingStatus.CANCELLED)
            throw new BookingCancelledException(booking.getId());
        Ticket ticket = ticketRepository.findById(booking.getTicketId())
                .orElseThrow(() -> new PaymentException("Ticket not found for booking"));
        if (ticket.getStatus() == TicketStatus.PAID)
            throw new PaymentException("Ticket has already been paid");
    }

    public String generatePaymentId() {
        return IdGenerator.generatePaymentId();
    }

    public void updatePaymentStatus(String paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Payment not found: " + paymentId));
        payment.setStatus(status);
        paymentRepository.update(payment);
    }

    public PaymentResponseDTO findPayment(String paymentId) {
        return paymentRepository.findById(paymentId).map(this::toResponse)
                .orElseThrow(() -> new PaymentException("Payment not found: " + paymentId));
    }

    private PaymentResponseDTO toResponse(Payment payment) {
        return new PaymentResponseDTO(payment.getId(), payment.getTicketId(), payment.getStatus(),
                payment.getPaymentTime());
    }
}