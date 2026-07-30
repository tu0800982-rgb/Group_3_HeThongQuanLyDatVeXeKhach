package com.busbooking.utils;

import com.busbooking.enums.CustomerType;
import com.busbooking.enums.SeatType;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PriceCalculator {
    private static final BigDecimal VIP_SURCHARGE_RATE = new BigDecimal("0.20");
    private static final BigDecimal MEMBER_DISCOUNT_RATE = new BigDecimal("0.10");
    private static final BigDecimal VIP_DISCOUNT_RATE = new BigDecimal("0.20");

    private PriceCalculator() {
    }

    public static BigDecimal calculateBasePrice(BigDecimal basePrice, SeatType seatType) {
        return seatType == SeatType.VIP ? basePrice.multiply(BigDecimal.ONE.add(VIP_SURCHARGE_RATE)) : basePrice;
    }

    public static BigDecimal calculateDiscount(BigDecimal subtotal, CustomerType customerType) {
        if (customerType == CustomerType.MEMBER)
            return subtotal.multiply(MEMBER_DISCOUNT_RATE);
        if (customerType == CustomerType.VIP)
            return subtotal.multiply(VIP_DISCOUNT_RATE);
        return BigDecimal.ZERO;
    }

    public static BigDecimal calculateFinalPrice(BigDecimal basePrice, SeatType seatType, CustomerType customerType) {
        BigDecimal subtotal = calculateBasePrice(basePrice, seatType);
        BigDecimal finalPrice = subtotal.subtract(calculateDiscount(subtotal, customerType)).setScale(0,
                RoundingMode.HALF_UP);
        if (finalPrice.signum() <= 0)
            throw new IllegalArgumentException("Final price must be greater than zero");
        return finalPrice;
    }
}