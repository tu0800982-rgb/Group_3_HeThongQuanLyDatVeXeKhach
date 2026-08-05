package com.busbooking.controller;

import com.busbooking.dto.ApiResponse;
import com.busbooking.dto.BookingRequestDTO;
import com.busbooking.dto.BookingResponseDTO;
import com.busbooking.dto.BookingDetailsResponseDTO;
import com.busbooking.service.BookingService;
import com.busbooking.service.AuthService;
import com.busbooking.enums.UserRole;
import com.busbooking.model.User;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final AuthService authService;

    public BookingController(BookingService bookingService, AuthService authService) {
        this.bookingService = bookingService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponseDTO>> bookTicket(@Valid @RequestBody BookingRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking created successfully", bookingService.bookTicket(request)));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponseDTO>> getBooking(@PathVariable String bookingId,
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        User user = authService.requireUser(token);
        if (user.getRole() != UserRole.ADMIN && !bookingService.belongsToCustomer(bookingId, user.getPhone()))
            throw new com.busbooking.exception.UnauthorizedException("Bạn không có quyền xem vé này.");
        return ResponseEntity
                .ok(ApiResponse.success("Booking retrieved successfully", bookingService.findBooking(bookingId)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<BookingDetailsResponseDTO>> searchBooking(@RequestParam String bookingId,
            @RequestParam String phone,
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        User user = authService.requireUser(token);
        if (user.getRole() != UserRole.ADMIN && !user.getPhone().equals(phone))
            throw new com.busbooking.exception.UnauthorizedException("Bạn không có quyền xem vé này.");
        return ResponseEntity.ok(ApiResponse.success("Booking retrieved successfully",
                bookingService.findBookingDetails(bookingId, phone)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getBookingHistory(@PathVariable String customerId,
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        User user = authService.requireUser(token);
        if (user.getRole() != UserRole.ADMIN && !bookingService.customerIdBelongsToPhone(customerId, user.getPhone()))
            throw new com.busbooking.exception.UnauthorizedException("Bạn không có quyền xem lịch sử vé này.");
        return ResponseEntity.ok(ApiResponse.success("Booking history retrieved successfully",
                bookingService.findBookingHistory(customerId)));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getMyBookingHistory(
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        return ResponseEntity.ok(ApiResponse.success("Booking history retrieved successfully",
                bookingService.findBookingHistoryByPhone(authService.requireUser(token).getPhone())));
    }

    @GetMapping("/my/details")
    public ResponseEntity<ApiResponse<List<BookingDetailsResponseDTO>>> getMyBookingDetails(
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        return ResponseEntity.ok(ApiResponse.success("Booking details retrieved successfully",
                bookingService.findBookingDetailsByPhone(authService.requireUser(token).getPhone())));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<BookingResponseDTO>> cancelBooking(@PathVariable String bookingId,
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-Session-Token", required = false) String token) {
        User user = authService.requireUser(token);
        if (user.getRole() != UserRole.ADMIN && !bookingService.belongsToCustomer(bookingId, user.getPhone()))
            throw new com.busbooking.exception.UnauthorizedException("Bạn không có quyền hủy vé này.");
        return ResponseEntity
                .ok(ApiResponse.success("Booking cancelled successfully", bookingService.cancelBooking(bookingId)));
    }
}
