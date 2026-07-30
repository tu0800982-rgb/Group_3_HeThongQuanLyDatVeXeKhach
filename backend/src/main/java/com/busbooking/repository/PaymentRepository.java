package com.busbooking.repository;

import com.busbooking.model.Payment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {
    private final List<Payment> payments = new ArrayList<>();

    public List<Payment> findAll() {
        return new ArrayList<>(payments);
    }

    public Optional<Payment> findById(String paymentId) {
        return payments.stream().filter(payment -> payment.getId().equals(paymentId)).findFirst();
    }

    public Optional<Payment> findByBookingId(String bookingId) {
        return payments.stream().filter(payment -> payment.getBookingId().equals(bookingId)).findFirst();
    }

    public Payment save(Payment payment) {
        payments.add(payment);
        return payment;
    }

    public Payment update(Payment payment) {
        delete(payment.getId());
        payments.add(payment);
        return payment;
    }

    public boolean delete(String paymentId) {
        return payments.removeIf(payment -> payment.getId().equals(paymentId));
    }

    public boolean exists(String paymentId) {
        return findById(paymentId).isPresent();
    }

    public long count() {
        return payments.size();
    }

    public void clear() {
        payments.clear();
    }
}