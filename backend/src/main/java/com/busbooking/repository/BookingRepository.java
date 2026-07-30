package com.busbooking.repository;

import com.busbooking.model.Booking;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    public Optional<Booking> findById(String bookingId) {
        return bookings.stream().filter(booking -> booking.getId().equals(bookingId)).findFirst();
    }

    public List<Booking> findByCustomer(String customerId) {
        return bookings.stream().filter(booking -> booking.getCustomerId().equals(customerId)).toList();
    }

    public Booking save(Booking booking) {
        bookings.add(booking);
        return booking;
    }

    public Booking update(Booking booking) {
        delete(booking.getId());
        bookings.add(booking);
        return booking;
    }

    public boolean delete(String bookingId) {
        return bookings.removeIf(booking -> booking.getId().equals(bookingId));
    }

    public boolean exists(String bookingId) {
        return findById(bookingId).isPresent();
    }

    public long count() {
        return bookings.size();
    }

    public void clear() {
        bookings.clear();
    }
}
