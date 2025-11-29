package com.example.foodknowledgehub.security.user;

import com.example.foodknowledgehub.security.user.dto.RegisterUserDto;
import com.example.foodknowledgehub.security.user.dto.UserDto;
import com.example.foodknowledgehub.repo.UsersRepository;
import com.example.foodknowledgehub.security.user.modal.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository,
                       PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UUID registerUser(RegisterUserDto registerUserDto) {
        usersRepository.findByUserName(registerUserDto.getUserName()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already taken");
        });

        Users user = new Users();
        user.setFullName(registerUserDto.getFullName());
        user.setUserName(registerUserDto.getUserName());
        user.setPasswordHash(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRole(Role.USER_ROLE);
        user.setAccountLocked(false);
        user.setCreatedAt(Instant.now());

        Users saved = usersRepository.save(user);
        return saved.getId();
    }

    public UserDto getCurrentUser(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        if (principal != null) {
            return toDto(principal.user());
        }
        return null;
    }

    public List<UserDto> getAllUsers() {
        return usersRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }


    private UserDto toDto(Users user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setUserName(user.getUserName());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setAccountLocked(user.isAccountLocked());
        return dto;
    }
}
