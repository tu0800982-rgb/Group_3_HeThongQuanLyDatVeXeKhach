package com.busbooking.dto;

import java.math.BigDecimal;

public class ReportResponseDTO {
    private BigDecimal totalRevenue;
    private long totalTrips;
    private long totalCustomers;
    private long totalBookings;
    private long cancelledBookings;
    private long paidTickets;
    private long vipTickets;
    private long availableSeats;
    private long bookedSeats;
    private long remainingSeats;

    public ReportResponseDTO() {
    }

    public ReportResponseDTO(BigDecimal totalRevenue, long totalTrips, long totalCustomers, long totalBookings,
            long cancelledBookings, long paidTickets, long vipTickets, long availableSeats, long bookedSeats,
            long remainingSeats) {
        this.totalRevenue = totalRevenue;
        this.totalTrips = totalTrips;
        this.totalCustomers = totalCustomers;
        this.totalBookings = totalBookings;
        this.cancelledBookings = cancelledBookings;
        this.paidTickets = paidTickets;
        this.vipTickets = vipTickets;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.remainingSeats = remainingSeats;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(long totalTrips) {
        this.totalTrips = totalTrips;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public long getPaidTickets() {
        return paidTickets;
    }

    public void setPaidTickets(long paidTickets) {
        this.paidTickets = paidTickets;
    }

    public long getVipTickets() {
        return vipTickets;
    }

    public void setVipTickets(long vipTickets) {
        this.vipTickets = vipTickets;
    }

    public long getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(long availableSeats) {
        this.availableSeats = availableSeats;
    }

    public long getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(long bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public long getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(long remainingSeats) {
        this.remainingSeats = remainingSeats;
    }
}
