package com.api.rest.canvas2.Course.application;

import com.api.rest.canvas2.Course.domain.CourseService;
import com.api.rest.canvas2.Course.dto.CourseRequestDto;
import com.api.rest.canvas2.Course.dto.CourseResponseDto;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto courseRequestDto) {
        CourseResponseDto createdCourse = courseService.createCourse(courseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<CourseResponseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        CourseResponseDto course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CourseResponseDto> getCourseByName(@PathVariable String name) {
        CourseResponseDto course = courseService.getCourseByName(name);
        return ResponseEntity.ok(course);
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(
            @PathVariable Long id, @RequestBody CourseRequestDto courseRequestDto) {
        CourseResponseDto updatedCourse = courseService.updateCourse(id, courseRequestDto);
        return ResponseEntity.ok(updatedCourse);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @GetMapping("/{courseId}/users")
    public ResponseEntity<List<UserResponseDto>> getUsersInCourse(@PathVariable Long courseId) {
        List<UserResponseDto> users = courseService.getUsersInCourse(courseId);
        return ResponseEntity.ok(users);
    }
}
