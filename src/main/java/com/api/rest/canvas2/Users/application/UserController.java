package com.api.rest.canvas2.Users.application;

import com.api.rest.canvas2.Users.domain.UserService;
import com.api.rest.canvas2.Users.dto.UserRequestDto;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    // Obtener un usuario por nombre
    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponseDto> getUserByName(@PathVariable String name) {
        UserResponseDto userResponse = userService.getUserByName(name);
        return ResponseEntity.ok(userResponse);
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener calificaciones de un usuario por curso
    @GetMapping("/{userId}/courses/{courseId}/grades")
    public ResponseEntity<List<GradeResponseDto>> getGradesByUserAndCourse(
            @PathVariable Long userId, @PathVariable Long courseId) {
        List<GradeResponseDto> grades = userService.getGradesByUserAndCourse(userId, courseId);
        return ResponseEntity.ok(grades);
    }
}