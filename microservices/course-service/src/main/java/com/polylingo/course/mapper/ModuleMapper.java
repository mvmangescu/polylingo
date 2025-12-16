package com.polylingo.course.mapper;

import com.polylingo.course.dto.ModuleResponse;
import com.polylingo.course.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "lessonCount", expression = "java(module.getLessons() != null ? module.getLessons().size() : 0)")
    ModuleResponse toResponse(Module module);

    List<ModuleResponse> toResponseList(List<Module> modules);
}
