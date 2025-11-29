package com.example.foodknowledgehub.api;

import com.example.foodknowledgehub.security.user.dto.RegisterUserDto;

import com.example.foodknowledgehub.security.user.UserService;
import com.example.foodknowledgehub.security.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v0")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        UUID id = userService.registerUser(registerUserDto);

        Map<String, Object> body = Map.of(
                "id", id,
                "message", "User registered successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


    //  curl -X GET http://localhost:8080/api/auth/login \                                                                      ✔  16:18:03 ▓▒░
    //  -u testuser:SuperSecret123
    @GetMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> body = Map.of(
                "message", "Login successful",
                "userName", authentication.getName()
        );

        return ResponseEntity.ok(body);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        UserDto dto = userService.getCurrentUser(authentication);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(dto);
    }
}
