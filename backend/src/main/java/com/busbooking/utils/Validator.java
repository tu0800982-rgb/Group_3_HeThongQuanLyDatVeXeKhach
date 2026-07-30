package com.busbooking.utils;

import com.busbooking.enums.CustomerType;
import com.busbooking.exception.ValidationException;
import java.util.regex.Pattern;

public final class Validator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private Validator() {
    }

    public static void validateCustomer(String fullName, String phone, String email, CustomerType customerType) {
        if (fullName == null || fullName.trim().length() < 2 || fullName.trim().length() > 100)
            throw new ValidationException("Customer name must contain 2 to 100 characters");
        validatePhone(phone);
        validateEmail(email);
        if (customerType == null)
            throw new ValidationException("Customer type is required");
    }

    public static void validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches())
            throw new ValidationException("Phone number must contain exactly 10 digits");
    }

    public static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches())
            throw new ValidationException("Email format is invalid");
    }

    public static void validateTripId(String tripId) {
        requireText(tripId, "Trip ID is required");
    }

    public static void validateSeatNumber(String seatNumber) {
        requireText(seatNumber, "Seat number is required");
    }

    public static void validateBookingId(String bookingId) {
        requireText(bookingId, "Booking ID is required");
    }

    private static void requireText(String value, String message) {
        if (value == null || value.isBlank())
            throw new ValidationException(message);
    }
}