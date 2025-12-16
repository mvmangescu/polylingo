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
public class LessonResponse {

    private Long id;
    private Long moduleId;
    private String title;
    private String description;
    private String lessonType;
    private Integer sequenceOrder;
    private Integer xpReward;
    private Boolean isLocked;
    private String contentUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
