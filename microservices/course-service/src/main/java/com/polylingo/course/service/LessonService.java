package com.polylingo.course.service;

import com.polylingo.course.dto.LessonResponse;
import com.polylingo.course.entity.Lesson;
import com.polylingo.course.exception.ResourceNotFoundException;
import com.polylingo.course.mapper.LessonMapper;
import com.polylingo.course.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Transactional(readOnly = true)
    public List<LessonResponse> getLessonsByModuleId(Long moduleId) {
        log.info("Fetching lessons for module id: {}", moduleId);
        return lessonMapper.toResponseList(
            lessonRepository.findByModuleIdOrderBySequenceOrderAsc(moduleId));
    }

    @Transactional(readOnly = true)
    public List<LessonResponse> getUnlockedLessonsByModuleId(Long moduleId) {
        log.info("Fetching unlocked lessons for module id: {}", moduleId);
        return lessonMapper.toResponseList(
            lessonRepository.findUnlockedLessonsByModuleId(moduleId));
    }

    @Transactional(readOnly = true)
    public LessonResponse getLessonById(Long id) {
        log.info("Fetching lesson with id: {}", id);
        Lesson lesson = lessonRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", id));
        return lessonMapper.toResponse(lesson);
    }

    @Transactional(readOnly = true)
    public List<LessonResponse> getLessonsByCourseId(Long courseId) {
        log.info("Fetching all lessons for course id: {}", courseId);
        return lessonMapper.toResponseList(lessonRepository.findAllByCourseId(courseId));
    }

    @Transactional
    public void unlockLesson(Long lessonId) {
        log.info("Unlocking lesson with id: {}", lessonId);
        Lesson lesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));
        lesson.setIsLocked(false);
        lessonRepository.save(lesson);
        log.info("Lesson unlocked with id: {}", lessonId);
    }
}
