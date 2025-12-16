package com.polylingo.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleResponse {

    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private Integer sequenceOrder;
    private Boolean isLocked;
    private Integer lessonCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
