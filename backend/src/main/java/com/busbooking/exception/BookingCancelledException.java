package com.busbooking.exception;

public class BookingCancelledException extends RuntimeException {
    public BookingCancelledException(String bookingId) {
        super("Booking is already cancelled: " + bookingId);
    }
}