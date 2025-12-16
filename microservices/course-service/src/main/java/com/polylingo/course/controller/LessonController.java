package com.polylingo.course.controller;

import com.polylingo.course.dto.LessonResponse;
import com.polylingo.course.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Tag(name = "Lessons", description = "Lesson management APIs")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/module/{moduleId}")
    @Operation(summary = "Get lessons by module ID",
        description = "Retrieve all lessons for a specific module")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lessons",
            content = @Content(schema = @Schema(implementation = LessonResponse.class)))
    })
    public ResponseEntity<List<LessonResponse>> getLessonsByModuleId(
            @Parameter(description = "Module ID") @PathVariable Long moduleId) {
        return ResponseEntity.ok(lessonService.getLessonsByModuleId(moduleId));
    }

    @GetMapping("/module/{moduleId}/unlocked")
    @Operation(summary = "Get unlocked lessons",
        description = "Retrieve all unlocked lessons for a specific module")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved unlocked lessons",
            content = @Content(schema = @Schema(implementation = LessonResponse.class)))
    })
    public ResponseEntity<List<LessonResponse>> getUnlockedLessonsByModuleId(
            @Parameter(description = "Module ID") @PathVariable Long moduleId) {
        return ResponseEntity.ok(lessonService.getUnlockedLessonsByModuleId(moduleId));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get all lessons by course ID",
        description = "Retrieve all lessons for a specific course across all modules")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lessons",
            content = @Content(schema = @Schema(implementation = LessonResponse.class)))
    })
    public ResponseEntity<List<LessonResponse>> getLessonsByCourseId(
            @Parameter(description = "Course ID") @PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourseId(courseId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by ID", description = "Retrieve a specific lesson by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lesson",
            content = @Content(schema = @Schema(implementation = LessonResponse.class))),
        @ApiResponse(responseCode = "404", description = "Lesson not found")
    })
    public ResponseEntity<LessonResponse> getLessonById(
            @Parameter(description = "Lesson ID") @PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @PatchMapping("/{id}/unlock")
    @Operation(summary = "Unlock a lesson",
        description = "Unlock a lesson to make it accessible to users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lesson unlocked successfully"),
        @ApiResponse(responseCode = "404", description = "Lesson not found")
    })
    public ResponseEntity<Void> unlockLesson(
            @Parameter(description = "Lesson ID") @PathVariable Long id) {
        lessonService.unlockLesson(id);
        return ResponseEntity.noContent().build();
    }
}
