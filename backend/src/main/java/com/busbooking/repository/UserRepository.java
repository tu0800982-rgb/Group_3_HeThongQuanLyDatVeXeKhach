package com.busbooking.repository;

import com.busbooking.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findByPhone(String phone) {
        return users.stream().filter(user -> user.getPhone().equals(phone)).findFirst();
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public void clear() {
        users.clear();
    }
}