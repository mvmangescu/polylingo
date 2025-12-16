package com.polylingo.course.service;

import com.polylingo.course.dto.CourseRequest;
import com.polylingo.course.dto.CourseResponse;
import com.polylingo.course.entity.Course;
import com.polylingo.course.entity.Tag;
import com.polylingo.course.entity.DifficultyLevel;
import com.polylingo.course.exception.ResourceNotFoundException;
import com.polylingo.course.mapper.CourseMapper;
import com.polylingo.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        log.info("Fetching all courses");
        return courseMapper.toResponseList(courseRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getPublishedCourses() {
        log.info("Fetching published courses");
        return courseMapper.toResponseList(courseRepository.findByIsPublishedTrue());
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        log.info("Fetching course with id: {}", id);
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        return courseMapper.toResponse(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByLanguages(String sourceLanguage, String targetLanguage) {
        log.info("Fetching courses for {} -> {}", sourceLanguage, targetLanguage);
        return courseMapper.toResponseList(
            courseRepository.findPublishedCoursesByLanguages(sourceLanguage, targetLanguage));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByLanguagesAndDifficulty(
            String sourceLanguage,
            String targetLanguage,
            DifficultyLevel difficultyLevel) {
        log.info("Fetching courses for {} -> {} with difficulty level: {}",
            sourceLanguage, targetLanguage, difficultyLevel);
        return courseMapper.toResponseList(
            courseRepository.findPublishedCoursesByLanguagesAndDifficulty(
                sourceLanguage, targetLanguage, difficultyLevel));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByTag(String tag) {
        log.info("Fetching courses with tag: {}", tag);
        return courseMapper.toResponseList(courseRepository.findPublishedCoursesByTag(tag));
    }

    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        log.info("Creating new course: {}", request.getTitle());

        if (request.getSourceLanguage().equals(request.getTargetLanguage())) {
            throw new IllegalArgumentException("Source and target languages must be different");
        }

        Course course = courseMapper.toEntity(request);

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            List<Tag> tags = request.getTags().stream()
                .map(tag -> Tag.builder()
                    .course(course)
                    .name(tag)
                    .build())
                .collect(Collectors.toList());
            course.setTags(tags);
        }

        Course savedCourse = courseRepository.save(course);
        log.info("Course created with id: {}", savedCourse.getId());
        return courseMapper.toResponse(savedCourse);
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        log.info("Updating course with id: {}", id);
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setSourceLanguage(request.getSourceLanguage());
        course.setTargetLanguage(request.getTargetLanguage());
        course.setDifficultyLevel(DifficultyLevel.valueOf(request.getDifficultyLevel()));
        course.setImageUrl(request.getImageUrl());
        course.setIsPublished(request.getIsPublished());
        course.setTotalXp(request.getTotalXp());
        course.setEstimatedHours(request.getEstimatedHours());

        Course updatedCourse = courseRepository.save(course);
        log.info("Course updated with id: {}", updatedCourse.getId());
        return courseMapper.toResponse(updatedCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course with id: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course", "id", id);
        }
        courseRepository.deleteById(id);
        log.info("Course deleted with id: {}", id);
    }
}
