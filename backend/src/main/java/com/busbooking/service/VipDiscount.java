package com.busbooking.service;

import com.busbooking.enums.CustomerType;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class VipDiscount implements DiscountPolicy {
    private static final BigDecimal RATE = new BigDecimal("0.20");

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.VIP;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(RATE);
    }
}