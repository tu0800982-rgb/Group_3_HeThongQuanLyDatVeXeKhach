package com.busbooking.model;

import java.util.Objects;
import com.busbooking.enums.UserRole;

public class User extends Person {
    private String username;
    private UserRole role;

    public User() {
    }

    public User(String id, String fullName, String phone, String email, String username) {
        this(id, fullName, phone, email, username, UserRole.CUSTOMER);
    }

    public User(String id, String fullName, String phone, String email, String username, UserRole role) {
        super(id, fullName, phone, email);
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + super.toString() + ", username='" + username + "'}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof User user))
            return false;
        return super.equals(object) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }
}