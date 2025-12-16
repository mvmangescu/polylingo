package com.polylingo.course.mapper;

import com.polylingo.course.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    default List<String> tagsToNames(List<Tag> tags) {
        if (tags == null) {
            return List.of();
        }
        return tags.stream()
                .map(Tag::getName)
                .toList();
    }
}
