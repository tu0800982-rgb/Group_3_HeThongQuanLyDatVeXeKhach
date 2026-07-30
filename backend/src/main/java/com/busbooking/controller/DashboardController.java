package com.busbooking.controller;

import com.busbooking.dto.ApiResponse;
import com.busbooking.dto.DashboardResponseDTO;
import com.busbooking.dto.ReportResponseDTO;
import com.busbooking.dto.BookingDetailsResponseDTO;
import com.busbooking.enums.BookingStatus;
import com.busbooking.enums.PaymentStatus;
import com.busbooking.service.BookingService;
import java.time.LocalDate;
import java.util.List;
import com.busbooking.service.DashboardService;
import com.busbooking.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staff")
public class DashboardController {
    private final DashboardService dashboardService;
    private final AuthService authService;
    private final BookingService bookingService;

    public DashboardController(DashboardService dashboardService, AuthService authService,
            BookingService bookingService) {
        this.dashboardService = dashboardService;
        this.authService = authService;
        this.bookingService = bookingService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardResponseDTO>> getDashboard(
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        authService.requireAdmin(token);
        return ResponseEntity
                .ok(ApiResponse.success("Dashboard retrieved successfully", dashboardService.getDashboard()));
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> getReport(
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        authService.requireAdmin(token);
        return ResponseEntity.ok(ApiResponse.success("Report retrieved successfully", dashboardService.getRevenue()));
    }

    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<BookingDetailsResponseDTO>>> getAllBookings(
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String keyword,
            @org.springframework.web.bind.annotation.RequestParam(required = false) BookingStatus bookingStatus,
            @org.springframework.web.bind.annotation.RequestParam(required = false) PaymentStatus paymentStatus,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String busCompany,
            @org.springframework.web.bind.annotation.RequestParam(required = false) LocalDate departureDate) {
        authService.requireAdmin(token);
        return ResponseEntity.ok(ApiResponse.success("All booking details retrieved successfully", bookingService
                .findAllBookingDetails(keyword, bookingStatus, paymentStatus, busCompany, departureDate)));
    }
}