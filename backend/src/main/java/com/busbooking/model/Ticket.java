package com.busbooking.model;

import com.busbooking.enums.TicketStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private String id;
    private String bookingId;
    private BigDecimal finalPrice;
    private TicketStatus status;
    private LocalDateTime issuedAt;

    public Ticket() {
    }

    public Ticket(String id, String bookingId, BigDecimal finalPrice, TicketStatus status, LocalDateTime issuedAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.finalPrice = finalPrice;
        this.status = status;
        this.issuedAt = issuedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    @Override
    public String toString() {
        return "Ticket{id='" + id + "', bookingId='" + bookingId + "', finalPrice=" + finalPrice + ", status=" + status
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Ticket ticket))
            return false;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
