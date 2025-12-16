package com.polylingo.course.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String description;

    @NotBlank(message = "Source language is required")
    @Pattern(regexp = "en|ro|es", message = "Source language must be one of: en, ro, es")
    private String sourceLanguage;

    @NotBlank(message = "Target language is required")
    @Pattern(regexp = "en|ro|es", message = "Target language must be one of: en, ro, es")
    private String targetLanguage;

    @NotBlank(message = "Difficulty level is required")
    @Pattern(regexp = "BEGINNER|INTERMEDIATE|ADVANCED|EXPERT", message = "Invalid difficulty level")
    private String difficultyLevel;

    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

    private Boolean isPublished = false;

    @Min(value = 0, message = "Total XP must be non-negative")
    private Integer totalXp = 0;

    @DecimalMin(value = "0.0", message = "Estimated hours must be non-negative")
    private BigDecimal estimatedHours;

    private List<String> tags;
}
