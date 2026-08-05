package com.busbooking.dto;

import com.busbooking.enums.PaymentMethodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequestDTO {
    @NotBlank
    private String bookingId;
    @NotNull
    private PaymentMethodType paymentMethod;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(String bookingId, PaymentMethodType paymentMethod) {
        this.bookingId = bookingId;
        this.paymentMethod = paymentMethod;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
