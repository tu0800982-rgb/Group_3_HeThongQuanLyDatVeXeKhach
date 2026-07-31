package com.busbooking.exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(String tripId, String seatNumber) {
        super("Seat " + seatNumber + " was not found for trip " + tripId);
    }
}