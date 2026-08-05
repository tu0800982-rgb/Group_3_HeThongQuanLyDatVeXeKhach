package com.busbooking.service;

import com.busbooking.enums.CustomerType;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class MemberDiscount implements DiscountPolicy {
    private static final BigDecimal RATE = new BigDecimal("0.10");

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.MEMBER;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(RATE);
    }
}