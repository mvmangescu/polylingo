package com.polylingo.course.controller;

import com.polylingo.course.dto.ModuleResponse;
import com.polylingo.course.service.ModuleService;
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
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(name = "Modules", description = "Module management APIs")
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get modules by course ID",
        description = "Retrieve all modules for a specific course")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved modules",
            content = @Content(schema = @Schema(implementation = ModuleResponse.class)))
    })
    public ResponseEntity<List<ModuleResponse>> getModulesByCourseId(
            @Parameter(description = "Course ID") @PathVariable Long courseId) {
        return ResponseEntity.ok(moduleService.getModulesByCourseId(courseId));
    }

    @GetMapping("/course/{courseId}/unlocked")
    @Operation(summary = "Get unlocked modules",
        description = "Retrieve all unlocked modules for a specific course")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved unlocked modules",
            content = @Content(schema = @Schema(implementation = ModuleResponse.class)))
    })
    public ResponseEntity<List<ModuleResponse>> getUnlockedModulesByCourseId(
            @Parameter(description = "Course ID") @PathVariable Long courseId) {
        return ResponseEntity.ok(moduleService.getUnlockedModulesByCourseId(courseId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get module by ID", description = "Retrieve a specific module by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved module",
            content = @Content(schema = @Schema(implementation = ModuleResponse.class))),
        @ApiResponse(responseCode = "404", description = "Module not found")
    })
    public ResponseEntity<ModuleResponse> getModuleById(
            @Parameter(description = "Module ID") @PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getModuleById(id));
    }

    @PatchMapping("/{id}/unlock")
    @Operation(summary = "Unlock a module",
        description = "Unlock a module to make it accessible to users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Module unlocked successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found")
    })
    public ResponseEntity<Void> unlockModule(
            @Parameter(description = "Module ID") @PathVariable Long id) {
        moduleService.unlockModule(id);
        return ResponseEntity.noContent().build();
    }
}
