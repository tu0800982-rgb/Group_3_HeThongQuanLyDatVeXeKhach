package com.busbooking.model;

import com.busbooking.enums.SeatStatus;
import com.busbooking.enums.SeatType;
import java.util.Objects;

public class Seat {
    private String id;
    private String tripId;
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus status;

    public Seat() {
    }

    public Seat(String id, String tripId, String seatNumber, SeatType seatType, SeatStatus status) {
        this.id = id;
        this.tripId = tripId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Seat{id='" + id + "', tripId='" + tripId + "', seatNumber='" + seatNumber + "', status=" + status + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Seat seat))
            return false;
        return Objects.equals(tripId, seat.tripId) && Objects.equals(seatNumber, seat.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, seatNumber);
    }
}
