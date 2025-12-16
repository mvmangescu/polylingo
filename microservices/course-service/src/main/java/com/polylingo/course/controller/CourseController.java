package com.polylingo.course.controller;

import com.polylingo.course.dto.CourseRequest;
import com.polylingo.course.dto.CourseResponse;
import com.polylingo.course.entity.DifficultyLevel;
import com.polylingo.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Course management APIs")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieve all courses (admin view)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved courses",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    })
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/published")
    @Operation(summary = "Get published courses", description = "Retrieve all published courses available to users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved published courses",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    })
    public ResponseEntity<List<CourseResponse>> getPublishedCourses() {
        return ResponseEntity.ok(courseService.getPublishedCourses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieve a specific course by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved course",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResponse> getCourseById(
            @Parameter(description = "Course ID") @PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search courses by languages",
            description = "Find courses by source and target language pair")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved courses",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    })
    public ResponseEntity<List<CourseResponse>> getCoursesByLanguages(
            @Parameter(description = "Source language (en, ro, es)")
            @RequestParam String sourceLanguage,
            @Parameter(description = "Target language (en, ro, es)")
            @RequestParam String targetLanguage,
            @Parameter(description = "Difficulty level (optional)")
            @RequestParam(required = false) DifficultyLevel difficultyLevel) {

        if (difficultyLevel != null) {
            return ResponseEntity.ok(courseService.getCoursesByLanguagesAndDifficulty(
                    sourceLanguage, targetLanguage, difficultyLevel));
        }
        return ResponseEntity.ok(courseService.getCoursesByLanguages(sourceLanguage, targetLanguage));
    }

    @GetMapping("/tags/{tag}")
    @Operation(summary = "Get courses by tag", description = "Find all published courses with a specific tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved courses",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    })
    public ResponseEntity<List<CourseResponse>> getCoursesByTag(
            @Parameter(description = "Tag name") @PathVariable String tag) {
        return ResponseEntity.ok(courseService.getCoursesByTag(tag));
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Create a new course (admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created successfully",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<CourseResponse> createCourse(
            @Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course", description = "Update an existing course (admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<CourseResponse> updateCourse(
            @Parameter(description = "Course ID") @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course", description = "Delete a course and all its modules/lessons (admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "Course ID") @PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
