package com.busbooking.service;

import com.busbooking.enums.CustomerType;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class NormalDiscount implements DiscountPolicy {
    @Override
    public CustomerType getCustomerType() {
        return CustomerType.NORMAL;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}