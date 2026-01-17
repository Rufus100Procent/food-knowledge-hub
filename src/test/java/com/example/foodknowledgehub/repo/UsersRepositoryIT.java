package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.helper.AbstractPostgresContainer;
import com.example.foodknowledgehub.security.user.Role;
import com.example.foodknowledgehub.security.user.modal.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class UsersRepositoryIT extends AbstractPostgresContainer {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void shouldFindUserByUsername() {
        Users info = new Users();
        info.setUserName("John");
        info.setRole(Role.ADMIN_ROLE);
        info.setPasswordHash("8jXoaX3omqCF+0ie3dIjTsTY/qbVKgMFH07rGAITeoE=");
        usersRepository.save(info);

        Optional<Users> result =
                usersRepository.findByUserName(info.getUserName());

        assertTrue(result.isPresent());
        assertEquals(info.getUserName(), result.get().getUserName());
        assertEquals(Role.ADMIN_ROLE, result.get().getRole());
    }
}
