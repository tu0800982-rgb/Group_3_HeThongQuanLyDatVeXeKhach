package com.busbooking.service;

import com.busbooking.enums.PaymentMethodType;
import com.busbooking.enums.PaymentStatus;
import com.busbooking.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class BankTransferPayment implements PaymentMethod {
    @Override
    public PaymentMethodType getType() {
        return PaymentMethodType.BANK_TRANSFER;
    }

    @Override
    public void process(Payment payment) {
        payment.setStatus(PaymentStatus.SUCCESS);
    }
}