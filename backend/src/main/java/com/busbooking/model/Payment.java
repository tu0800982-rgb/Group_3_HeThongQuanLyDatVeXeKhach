package com.busbooking.model;

import com.busbooking.enums.PaymentMethodType;
import com.busbooking.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private String id;
    private String bookingId;
    private String ticketId;
    private BigDecimal amount;
    private PaymentMethodType paymentMethod;
    private PaymentStatus status;
    private LocalDateTime paymentTime;

    public Payment() {
    }

    public Payment(String id, String bookingId, String ticketId, BigDecimal amount, PaymentMethodType paymentMethod,
            PaymentStatus status, LocalDateTime paymentTime) {
        this.id = id;
        this.bookingId = bookingId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentTime = paymentTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Override
    public String toString() {
        return "Payment{id='" + id + "', bookingId='" + bookingId + "', ticketId='" + ticketId + "', amount=" + amount
                + ", status=" + status + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Payment payment))
            return false;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}