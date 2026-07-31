package com.busbooking.model;

import com.busbooking.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private String id;
    private String customerId;
    private String tripId;
    private String seatNumber;
    private String ticketId;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;

    public Booking() {
    }

    public Booking(String id, String customerId, String tripId, String seatNumber, String ticketId,
            BigDecimal totalPrice, BookingStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.tripId = tripId;
        this.seatNumber = seatNumber;
        this.ticketId = ticketId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    @Override
    public String toString() {
        return "Booking{id='" + id + "', customerId='" + customerId + "', tripId='" + tripId + "', seatNumber='"
                + seatNumber + "', status=" + status + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Booking booking))
            return false;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}