package com.busbooking.service;

import com.busbooking.dto.AuthResponseDTO;
import com.busbooking.dto.LoginRequestDTO;
import com.busbooking.dto.RegisterRequestDTO;
import com.busbooking.dto.ProfileUpdateRequestDTO;
import com.busbooking.enums.UserRole;
import com.busbooking.exception.UnauthorizedException;
import com.busbooking.exception.ValidationException;
import com.busbooking.model.User;
import com.busbooking.repository.UserRepository;
import com.busbooking.utils.IdGenerator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final Map<String, User> sessions = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        String phone = normalizePhone(request.getPhone());
        if (userRepository.findByPhone(phone).isPresent())
            throw new ValidationException("Số điện thoại này đã được đăng ký");
        User user = userRepository.save(new User(IdGenerator.generateUserId(), request.getFullName().trim(), phone, "",
                phone, UserRole.CUSTOMER));
        return createSession(user);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        String phone = normalizePhone(request.getPhone());
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new UnauthorizedException("Số điện thoại chưa được đăng ký"));
        return createSession(user);
    }

    public void requireAdmin(String token) {
        User user = requireUser(token);
        if (user == null || user.getRole() != UserRole.ADMIN)
            throw new UnauthorizedException("Bạn cần đăng nhập bằng tài khoản quản trị viên để xem dashboard");
    }

    public AuthResponseDTO updateProfile(String token, ProfileUpdateRequestDTO request) {
        User user = requireUser(token);
        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail() == null ? "" : request.getEmail().trim());
        return toResponse(user, token);
    }

    public User requireUser(String token) {
        User user = token == null ? null : sessions.get(token);
        if (user == null)
            throw new UnauthorizedException("Bạn cần đăng nhập để sử dụng tính năng này");
        return user;
    }

    private AuthResponseDTO createSession(User user) {
        String token = UUID.randomUUID().toString();
        sessions.put(token, user);
        return toResponse(user, token);
    }

    private AuthResponseDTO toResponse(User user, String token) {
        return new AuthResponseDTO(user.getId(), user.getFullName(), user.getPhone(), user.getEmail(), user.getRole(), token);
    }

    private String normalizePhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}"))
            throw new ValidationException("Số điện thoại phải gồm đúng 10 chữ số");
        return phone;
    }
}
