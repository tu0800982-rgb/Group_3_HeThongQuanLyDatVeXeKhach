package com.busbooking.model;

import com.busbooking.enums.CustomerType;
import java.util.Objects;

public class Customer extends User {
    private CustomerType customerType;

    public Customer() {
    }

    public Customer(String id, String fullName, String phone, String email, String username,
            CustomerType customerType) {
        super(id, fullName, phone, email, username);
        this.customerType = customerType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer{" + super.toString() + ", customerType=" + customerType + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Customer customer))
            return false;
        return super.equals(object) && customerType == customer.customerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerType);
    }
}