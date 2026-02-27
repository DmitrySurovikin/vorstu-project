package dev.vorstu.mappers;

import dev.vorstu.dto.StudentDto;
import dev.vorstu.entity.Student;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-27T19:16:59+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Amazon.com Inc.)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public StudentDto toDto(Student entity) {
        if ( entity == null ) {
            return null;
        }

        StudentDto studentDto = new StudentDto();

        studentDto.setId( entity.getId() );
        studentDto.setFio( entity.getFio() );
        studentDto.setPhoneNumber( entity.getPhoneNumber() );
        studentDto.setGroup( groupMapper.toDto( entity.getGroup() ) );

        return studentDto;
    }

    @Override
    public Student toEntity(StudentDto dto) {
        if ( dto == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( dto.getId() );
        student.setFio( dto.getFio() );
        student.setGroup( groupMapper.toEntity( dto.getGroup() ) );
        student.setPhoneNumber( dto.getPhoneNumber() );

        return student;
    }
}
