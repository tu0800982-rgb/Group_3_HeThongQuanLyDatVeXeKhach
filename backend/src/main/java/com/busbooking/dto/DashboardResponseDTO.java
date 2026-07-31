package com.busbooking.dto;

import java.math.BigDecimal;

public class DashboardResponseDTO {
    private BigDecimal todayRevenue;
    private long todayBookings;
    private long todayCancelledBookings;
    private long totalBookings;
    private long confirmedBookings;
    private long availableSeats;
    private long bookedSeats;
    private long vipCustomers;
    private long normalCustomers;
    private long upcomingTrips;
    private long completedTrips;

    public DashboardResponseDTO() {
    }

    public DashboardResponseDTO(BigDecimal todayRevenue, long todayBookings, long todayCancelledBookings,
            long availableSeats, long bookedSeats, long vipCustomers, long normalCustomers, long upcomingTrips,
            long completedTrips) {
        this.todayRevenue = todayRevenue;
        this.todayBookings = todayBookings;
        this.todayCancelledBookings = todayCancelledBookings;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.vipCustomers = vipCustomers;
        this.normalCustomers = normalCustomers;
        this.upcomingTrips = upcomingTrips;
        this.completedTrips = completedTrips;
    }

    public DashboardResponseDTO(BigDecimal todayRevenue, long todayBookings, long todayCancelledBookings,
            long totalBookings, long confirmedBookings, long availableSeats, long bookedSeats, long vipCustomers,
            long normalCustomers, long upcomingTrips, long completedTrips) {
        this.todayRevenue = todayRevenue;
        this.todayBookings = todayBookings;
        this.todayCancelledBookings = todayCancelledBookings;
        this.totalBookings = totalBookings;
        this.confirmedBookings = confirmedBookings;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.vipCustomers = vipCustomers;
        this.normalCustomers = normalCustomers;
        this.upcomingTrips = upcomingTrips;
        this.completedTrips = completedTrips;
    }

    public BigDecimal getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(BigDecimal todayRevenue) {
        this.todayRevenue = todayRevenue;
    }

    public long getTodayBookings() {
        return todayBookings;
    }

    public void setTodayBookings(long todayBookings) {
        this.todayBookings = todayBookings;
    }

    public long getTodayCancelledBookings() {
        return todayCancelledBookings;
    }

    public void setTodayCancelledBookings(long todayCancelledBookings) {
        this.todayCancelledBookings = todayCancelledBookings;
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

    public long getVipCustomers() {
        return vipCustomers;
    }

    public void setVipCustomers(long vipCustomers) {
        this.vipCustomers = vipCustomers;
    }

    public long getNormalCustomers() {
        return normalCustomers;
    }

    public void setNormalCustomers(long normalCustomers) {
        this.normalCustomers = normalCustomers;
    }

    public long getUpcomingTrips() {
        return upcomingTrips;
    }

    public void setUpcomingTrips(long upcomingTrips) {
        this.upcomingTrips = upcomingTrips;
    }

    public long getCompletedTrips() {
        return completedTrips;
    }

    public void setCompletedTrips(long completedTrips) {
        this.completedTrips = completedTrips;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(long confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }
}
