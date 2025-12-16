package com.polylingo.course.repository;

import com.polylingo.course.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByCourseIdOrderBySequenceOrderAsc(Long courseId);

    @Query("SELECT m FROM Module m WHERE m.course.id = :courseId AND m.isLocked = false ORDER BY m.sequenceOrder ASC")
    List<Module> findUnlockedModulesByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT m FROM Module m WHERE m.course.id = :courseId AND m.sequenceOrder = :sequenceOrder")
    Module findByCourseIdAndSequenceOrder(@Param("courseId") Long courseId, @Param("sequenceOrder") Integer sequenceOrder);
}
