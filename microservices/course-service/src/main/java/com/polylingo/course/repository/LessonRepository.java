package com.polylingo.course.repository;

import com.polylingo.course.entity.Lesson;
import com.polylingo.course.entity.LessonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByModuleIdOrderBySequenceOrderAsc(Long moduleId);

    @Query("SELECT l FROM Lesson l WHERE l.module.id = :moduleId AND l.isLocked = false ORDER BY l.sequenceOrder ASC")
    List<Lesson> findUnlockedLessonsByModuleId(@Param("moduleId") Long moduleId);

    List<Lesson> findByLessonType(LessonType lessonType);

    @Query("SELECT l FROM Lesson l WHERE l.module.id = :moduleId AND l.sequenceOrder = :sequenceOrder")
    Lesson findByModuleIdAndSequenceOrder(@Param("moduleId") Long moduleId, @Param("sequenceOrder") Integer sequenceOrder);

    @Query("SELECT l FROM Lesson l JOIN l.module m WHERE m.course.id = :courseId ORDER BY m.sequenceOrder, l.sequenceOrder")
    List<Lesson> findAllByCourseId(@Param("courseId") Long courseId);
}
