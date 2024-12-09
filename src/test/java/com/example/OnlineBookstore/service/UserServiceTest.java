package com.example.OnlineBookstore.service;

import com.example.OnlineBookstore.model.User;
import com.example.OnlineBookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User("John Doe", "john.doe@example.com", "password123", "USER");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User("John Doe", "john.doe@example.com", "password123", "USER"),
                new User("Jane Doe", "jane.doe@example.com", "password456", "ADMIN")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() {
        Long userId = 1L;
        User user = new User("John Doe", "john.doe@example.com", "password123", "USER");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_NotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(userId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserByEmail_Found() {
        String email = "john.doe@example.com";
        User user = new User("John Doe", email, "password123", "USER");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetUserByEmail_NotFound() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByEmail(email);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        User existingUser = new User("Existing User", "existing@example.com", "password123", "USER");
        User updatedUser = new User("Updated User", "updated@example.com", "newpassword", "ADMIN");

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("Updated User", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testUpdateUser_NotFound() {
        Long userId = 1L;
        User updatedUser = new User("Updated User", "updated@example.com", "newpassword", "ADMIN");

        when(userRepository.existsById(userId)).thenReturn(false);

        User result = userService.updateUser(userId, updatedUser);

        assertNull(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
