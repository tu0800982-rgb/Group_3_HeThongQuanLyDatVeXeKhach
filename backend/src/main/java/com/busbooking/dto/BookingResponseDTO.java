package com.busbooking.dto;

import com.busbooking.enums.BookingStatus;
import java.math.BigDecimal;

public class BookingResponseDTO {
    private String bookingId;
    private String ticketId;
    private String seatNumber;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private String customerName;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(String bookingId, String ticketId, String seatNumber, BigDecimal totalPrice,
            BookingStatus status, String customerName) {
        this.bookingId = bookingId;
        this.ticketId = ticketId;
        this.seatNumber = seatNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.customerName = customerName;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
