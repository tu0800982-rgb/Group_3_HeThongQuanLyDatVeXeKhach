package com.busbooking.exception;

public class SeatBookedException extends RuntimeException {
    public SeatBookedException(String seatNumber) {
        super("Seat is no longer available: " + seatNumber);
    }
}