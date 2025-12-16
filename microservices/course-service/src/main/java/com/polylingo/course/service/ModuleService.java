package com.polylingo.course.service;

import com.polylingo.course.dto.ModuleResponse;
import com.polylingo.course.entity.Module;
import com.polylingo.course.exception.ResourceNotFoundException;
import com.polylingo.course.mapper.ModuleMapper;
import com.polylingo.course.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    @Transactional(readOnly = true)
    public List<ModuleResponse> getModulesByCourseId(Long courseId) {
        log.info("Fetching modules for course id: {}", courseId);
        return moduleMapper.toResponseList(
            moduleRepository.findByCourseIdOrderBySequenceOrderAsc(courseId));
    }

    @Transactional(readOnly = true)
    public List<ModuleResponse> getUnlockedModulesByCourseId(Long courseId) {
        log.info("Fetching unlocked modules for course id: {}", courseId);
        return moduleMapper.toResponseList(
            moduleRepository.findUnlockedModulesByCourseId(courseId));
    }

    @Transactional(readOnly = true)
    public ModuleResponse getModuleById(Long id) {
        log.info("Fetching module with id: {}", id);
        Module module = moduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        return moduleMapper.toResponse(module);
    }

    @Transactional
    public void unlockModule(Long moduleId) {
        log.info("Unlocking module with id: {}", moduleId);
        Module module = moduleRepository.findById(moduleId)
            .orElseThrow(() -> new ResourceNotFoundException("Module", "id", moduleId));
        module.setIsLocked(false);
        moduleRepository.save(module);
        log.info("Module unlocked with id: {}", moduleId);
    }
}
