package com.polylingo.course.repository;

import com.polylingo.course.entity.Course;
import com.polylingo.course.entity.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByIsPublishedTrue();

    List<Course> findBySourceLanguageAndTargetLanguage(String sourceLanguage, String targetLanguage);

    List<Course> findByDifficultyLevel(DifficultyLevel difficultyLevel);

    @Query("SELECT c FROM Course c WHERE c.isPublished = true " +
           "AND c.sourceLanguage = :sourceLanguage " +
           "AND c.targetLanguage = :targetLanguage")
    List<Course> findPublishedCoursesByLanguages(
        @Param("sourceLanguage") String sourceLanguage,
        @Param("targetLanguage") String targetLanguage
    );

    @Query("SELECT c FROM Course c WHERE c.isPublished = true " +
           "AND c.sourceLanguage = :sourceLanguage " +
           "AND c.targetLanguage = :targetLanguage " +
           "AND c.difficultyLevel = :difficultyLevel")
    List<Course> findPublishedCoursesByLanguagesAndDifficulty(
        @Param("sourceLanguage") String sourceLanguage,
        @Param("targetLanguage") String targetLanguage,
        @Param("difficultyLevel") DifficultyLevel difficultyLevel
    );

    @Query("SELECT DISTINCT c FROM Course c JOIN c.tags t WHERE t.name = :tag AND c.isPublished = true")
    List<Course> findPublishedCoursesByTag(@Param("tag") String tag);
}
