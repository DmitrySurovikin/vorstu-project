package dev.vorstu.mappers;

import dev.vorstu.dto.StudentDto;
import dev.vorstu.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = GroupMapper.class)
public interface StudentMapper {
    StudentDto toDto(Student entity);
    Student toEntity(StudentDto dto);
}
