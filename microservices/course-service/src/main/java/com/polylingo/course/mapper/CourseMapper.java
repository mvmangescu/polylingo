package com.polylingo.course.mapper;

import com.polylingo.course.dto.CourseRequest;
import com.polylingo.course.dto.CourseResponse;
import com.polylingo.course.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Course toEntity(CourseRequest request);

    @Mapping(target = "moduleCount", expression = "java(course.getModules() != null ? course.getModules().size() : 0)")
    CourseResponse toResponse(Course course);

    List<CourseResponse> toResponseList(List<Course> courses);
}
