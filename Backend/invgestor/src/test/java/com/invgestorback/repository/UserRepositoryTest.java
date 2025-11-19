package com.invgestorback.repository;

import com.invgestorback.model.Role;
import com.invgestorback.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <-- Add this line
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository; //this injects UserRepository

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldFindUserByEmail() {
        User user = new User();
        user.setEmail("test@invgestor.com");
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail(user.getEmail());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(user.getEmail(), found.get().getEmail());
    }

    @Test
    void testSaveAndFindUserByEmail() {
        Role role = new Role("ADMIN");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("Juan");
        user.setLastName("Tester");
        user.setEmail("juan@test.com");
        user.setPassword("encoded123");
        user.getRoles().add(role);
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("juan@test.com");
        Assertions.assertTrue(found.isPresent());
        Assertions.assertTrue(found.get().getRoles().contains(role));

    }
}
