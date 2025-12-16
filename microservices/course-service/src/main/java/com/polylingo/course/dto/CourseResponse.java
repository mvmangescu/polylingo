package com.polylingo.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private String sourceLanguage;
    private String targetLanguage;
    private String difficultyLevel;
    private String imageUrl;
    private Boolean isPublished;
    private Integer totalXp;
    private BigDecimal estimatedHours;
    private Integer moduleCount;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
