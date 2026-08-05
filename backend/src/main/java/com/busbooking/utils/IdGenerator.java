package com.busbooking.utils;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {
    private static final AtomicLong SEQUENCE = new AtomicLong(1000);

    private IdGenerator() {
    }

    public static String generateCustomerId() {
        return next("CUS");
    }

    public static String generateBookingId() {
        return next("BKG");
    }

    public static String generateTicketId() {
        return next("TKT");
    }

    public static String generatePaymentId() {
        return next("PAY");
    }

    public static String generateTripId() {
        return next("TRP");
    }

    public static String generateUserId() {
        return next("USR");
    }

    private static String next(String prefix) {
        return prefix + "-" + SEQUENCE.incrementAndGet();
    }
}