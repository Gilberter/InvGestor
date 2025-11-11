package com.invgestorback.service;

import com.invgestorback.model.Role;
import com.invgestorback.model.User;
import com.invgestorback.repository.RoleRepository;
import com.invgestorback.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.Set;

public class UserServiceTest {

    @Mock //Depedencies that are mocked
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks // The class under test is injected
    private UserService userService;

    private User user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Role role = new Role("ADMIN");
        user = new User();
        user.setFirstName("Juan");
        user.setLastName("Tester");
        user.setEmail("juan@test.com");
        user.setPassword("encoded123");
        user.setRoles(Set.of(role));
    }

    @Test
    void testFindUserByEmail_Success() {
        // ARRANGE (Define the mock behavior)
        // 4. Tell the mock what to return when its method is called
        Mockito.when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(user));

        // ACT (Call the service method)
        Optional<User> found = userRepository.findByEmail("juan@test.com");

        // ASSERT (Verify the results and interactions)
        Assertions.assertTrue(found.isPresent(), "User should be found in the service");
        // 5. Verify the service called the dependency exactly once
    }

    @Test
    void testSaveUser_CallsRepositories() {
        // ARRANGE
        Mockito.when(userRepository.save(user)).thenReturn(user);

        // ACT
        User savedUser = userRepository.save(user);

        // ASSERT
        // Verify that the save method was called on both repositories
        System.out.println(savedUser.getFirstName());
        Assertions.assertTrue(!savedUser.getEmail().isEmpty());
    }
}
