package com.busbooking.repository;

import com.busbooking.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {
    private final List<Customer> customers = new ArrayList<>();

    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    public Optional<Customer> findById(String customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst();
    }

    public Customer save(Customer customer) {
        customers.add(customer);
        return customer;
    }

    public Customer update(Customer customer) {
        delete(customer.getId());
        customers.add(customer);
        return customer;
    }

    public boolean delete(String customerId) {
        return customers.removeIf(customer -> customer.getId().equals(customerId));
    }

    public boolean exists(String customerId) {
        return findById(customerId).isPresent();
    }

    public long count() {
        return customers.size();
    }

    public void clear() {
        customers.clear();
    }
}