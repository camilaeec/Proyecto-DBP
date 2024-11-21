package com.api.rest.canvas2.user;

import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.dto.UserRequestDto;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.Users.domain.UserService;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    private UserResponseDto userResponseDto;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setLastname("User");
        user.setEmail("testuser@example.com");

        userRequestDto = new UserRequestDto();
        userRequestDto.setName("Test");
        userRequestDto.setLastname("User");
        userRequestDto.setEmail("testuser@example.com");

        userResponseDto = new UserResponseDto();
        userResponseDto.setId(1L);
        userResponseDto.setName("Test");
        userResponseDto.setLastname("User");
        userResponseDto.setEmail("testuser@example.com");
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getName());
        assertEquals("testuser@example.com", result.getEmail());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(1L);
        });
        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto result = userService.updateUser(1L, userRequestDto);
        assertNotNull(result);
        assertEquals("Test", result.getName());
        assertEquals("User", result.getLastname());
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(1L, userRequestDto);
        });
        assertEquals("User not found with id: 1", exception.getMessage());
    }
}
