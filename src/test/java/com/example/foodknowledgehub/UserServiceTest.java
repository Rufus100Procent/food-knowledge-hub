package com.example.foodknowledgehub;

import com.example.foodknowledgehub.repo.UsersRepository;
import com.example.foodknowledgehub.security.user.dto.RegisterUserDto;
import com.example.foodknowledgehub.security.user.Role;
import com.example.foodknowledgehub.security.user.UserPrincipal;
import com.example.foodknowledgehub.security.user.UserService;
import com.example.foodknowledgehub.security.user.dto.UserDto;
import com.example.foodknowledgehub.security.user.modal.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(usersRepository, passwordEncoder);
    }

    @Test
    void registerUser_savesUserWithEncodedPasswordAndReturnsId() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFullName("Test User");
        dto.setUserName("testUser");
        dto.setPassword("Secret123");

        when(usersRepository.findByUserName("testUser"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("Secret123"))
                .thenReturn("encoded-secret");

        when(usersRepository.save(any(Users.class)))
                .thenAnswer(invocation -> {
                    Users u = invocation.getArgument(0);
                    u.setId(UUID.randomUUID());
                    return u;
                });

        UUID id = userService.registerUser(dto);

        assertNotNull(id);

        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository).save(userCaptor.capture());
        Users saved = userCaptor.getValue();

        assertEquals("Test User", saved.getFullName());
        assertEquals("testUser", saved.getUserName());
        assertEquals("encoded-secret", saved.getPasswordHash());
        assertEquals(Role.USER_ROLE, saved.getRole());
        assertFalse(saved.isAccountLocked());
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void registerUser_throwsWhenUsernameAlreadyTaken() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFullName("Existing User");
        dto.setUserName("takenUser");
        dto.setPassword("Secret123");

        Users existing = new Users();
        existing.setId(UUID.randomUUID());
        existing.setUserName("takenUser");

        when(usersRepository.findByUserName("takenUser"))
                .thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(dto));

        verify(usersRepository, never()).save(any(Users.class));
    }

    @Test
    void getCurrentUser_returnsMappedDtoFromAuthenticationPrincipal() {
        Users user = new Users();
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setFullName("Auth User");
        user.setUserName("authUser");
        user.setPasswordHash("hash");
        user.setRole(Role.USER_ROLE);
        user.setAccountLocked(false);
        user.setCreatedAt(Instant.now());

        UserPrincipal principal = new UserPrincipal(user);

        when(authentication.getPrincipal())
                .thenReturn(principal);

        UserDto dto = userService.getCurrentUser(authentication);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Auth User", dto.getFullName());
        assertEquals("authUser", dto.getUserName());
        assertEquals(Role.USER_ROLE, dto.getRole());
        assertFalse(dto.isAccountLocked());
        assertNotNull(dto.getCreatedAt());
    }

    @Test
    void getCurrentUser_returnsNullWhenPrincipalIsNull() {
        when(authentication.getPrincipal())
                .thenReturn(null);

        UserDto dto = userService.getCurrentUser(authentication);

        assertNull(dto);
    }

    @Test
    void getAllUsers_mapsEntitiesToDtos() {
        Users u1 = new Users();
        u1.setId(UUID.randomUUID());
        u1.setFullName("User One");
        u1.setUserName("user1");
        u1.setPasswordHash("hash1");
        u1.setRole(Role.USER_ROLE);
        u1.setAccountLocked(false);
        u1.setCreatedAt(Instant.now());

        Users u2 = new Users();
        u2.setId(UUID.randomUUID());
        u2.setFullName("User Two");
        u2.setUserName("user2");
        u2.setPasswordHash("hash2");
        u2.setRole(Role.ADMIN_ROLE);
        u2.setAccountLocked(true);
        u2.setCreatedAt(Instant.now());

        when(usersRepository.findAll())
                .thenReturn(List.of(u1, u2));

        List<UserDto> result = userService.getAllUsers();

        assertEquals(2, result.size());

        UserDto d1 = result.getFirst();
        assertEquals(u1.getId(), d1.getId());
        assertEquals("User One", d1.getFullName());
        assertEquals("user1", d1.getUserName());
        assertEquals(Role.USER_ROLE, d1.getRole());
        assertFalse(d1.isAccountLocked());

        UserDto d2 = result.get(1);
        assertEquals(u2.getId(), d2.getId());
        assertEquals("User Two", d2.getFullName());
        assertEquals("user2", d2.getUserName());
        assertEquals(Role.ADMIN_ROLE, d2.getRole());
        assertTrue(d2.isAccountLocked());
    }
}
