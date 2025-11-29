package com.example.foodknowledgehub;

import com.example.foodknowledgehub.helper.AbstractPostgresContainer;
import com.example.foodknowledgehub.repo.UsersRepository;
import com.example.foodknowledgehub.security.user.Role;
import com.example.foodknowledgehub.security.user.modal.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UsersRepositoryIT extends AbstractPostgresContainer {

    @Autowired
    private UsersRepository usersRepository;

    private Users baseUser;

    @BeforeEach
    void setUp() {
        usersRepository.deleteAll();

        Users user = new Users();
        user.setFullName("Test User");
        user.setUserName("testuser");
        user.setPasswordHash("hashed-password");
        user.setRole(Role.USER_ROLE);
        user.setAccountLocked(false);
        user.setCreatedAt(Instant.now());

        baseUser = usersRepository.save(user);
    }

    @Test
    void saveAndLoadUser() {
        Users found = usersRepository.findById(baseUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertUser(
                found,
                baseUser.getId(),
                "Test User",
                "testuser",
                "hashed-password",
                Role.USER_ROLE,
                false
        );
        assertNotNull(found.getCreatedAt());
    }

    @Test
    void findByUserName_returnsUser() {
        Optional<Users> foundOpt = usersRepository.findByUserName("testuser");

        assertTrue(foundOpt.isPresent());
        Users found = foundOpt.get();

        assertUser(
                found,
                baseUser.getId(),
                "Test User",
                "testuser",
                "hashed-password",
                Role.USER_ROLE,
                false
        );
    }

    @Test
    void updateUser_updatesFields() {
        baseUser.setFullName("Updated Name");
        baseUser.setAccountLocked(true);
        baseUser.setPasswordHash("new-hash");

        usersRepository.save(baseUser);

        Users updated = usersRepository.findById(baseUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found after update"));

        assertUser(
                updated,
                baseUser.getId(),
                "Updated Name",
                "testuser",
                "new-hash",
                Role.USER_ROLE,
                true
        );
    }

    @Test
    void deleteUser_removesFromDatabase() {
        UUID id = baseUser.getId();

        usersRepository.deleteById(id);

        Optional<Users> result = usersRepository.findById(id);
        assertTrue(result.isEmpty());
    }

    private void assertUser(
            Users user,
            UUID expectedId,
            String expectedFullName,
            String expectedUserName,
            String expectedPasswordHash,
            Role expectedRole,
            boolean expectedAccountLocked
    ) {
        assertEquals(expectedId, user.getId());
        assertEquals(expectedFullName, user.getFullName());
        assertEquals(expectedUserName, user.getUserName());
        assertEquals(expectedPasswordHash, user.getPasswordHash());
        assertEquals(expectedRole, user.getRole());
        assertEquals(expectedAccountLocked, user.isAccountLocked());
    }
}
