package com.busbooking.service;

import com.busbooking.enums.CustomerType;
import java.math.BigDecimal;

public interface DiscountPolicy {
    CustomerType getCustomerType();

    BigDecimal calculateDiscount(BigDecimal amount);
}