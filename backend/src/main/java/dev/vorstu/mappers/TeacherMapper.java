package dev.vorstu.mappers;

import dev.vorstu.dto.TeacherDto;
import dev.vorstu.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = GroupMapper.class)
public interface TeacherMapper {
    TeacherDto toDto(Teacher entity);
    Teacher toEntity(TeacherDto dto);
}
