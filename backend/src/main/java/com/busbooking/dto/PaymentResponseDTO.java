package com.busbooking.dto;

import com.busbooking.enums.PaymentStatus;
import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private String paymentId;
    private String ticketId;
    private PaymentStatus status;
    private LocalDateTime paymentTime;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(String paymentId, String ticketId, PaymentStatus status, LocalDateTime paymentTime) {
        this.paymentId = paymentId;
        this.ticketId = ticketId;
        this.status = status;
        this.paymentTime = paymentTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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
}
