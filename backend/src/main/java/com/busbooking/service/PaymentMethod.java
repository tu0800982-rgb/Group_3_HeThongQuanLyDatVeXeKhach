package com.busbooking.service;

import com.busbooking.enums.PaymentMethodType;
import com.busbooking.model.Payment;

public interface PaymentMethod {
    PaymentMethodType getType();

    void process(Payment payment);
}