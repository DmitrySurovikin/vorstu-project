package dev.vorstu.mappers;

import dev.vorstu.dto.GroupDto;
import dev.vorstu.dto.TeacherDto;
import dev.vorstu.entity.Group;
import dev.vorstu.entity.Teacher;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-27T19:16:59+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Amazon.com Inc.)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public TeacherDto toDto(Teacher entity) {
        if ( entity == null ) {
            return null;
        }

        TeacherDto teacherDto = new TeacherDto();

        teacherDto.setId( entity.getId() );
        teacherDto.setFio( entity.getFio() );
        teacherDto.setGroups( groupSetToGroupDtoSet( entity.getGroups() ) );

        return teacherDto;
    }

    @Override
    public Teacher toEntity(TeacherDto dto) {
        if ( dto == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setId( dto.getId() );
        teacher.setFio( dto.getFio() );
        teacher.setGroups( groupDtoSetToGroupSet( dto.getGroups() ) );

        return teacher;
    }

    protected Set<GroupDto> groupSetToGroupDtoSet(Set<Group> set) {
        if ( set == null ) {
            return null;
        }

        Set<GroupDto> set1 = new LinkedHashSet<GroupDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Group group : set ) {
            set1.add( groupMapper.toDto( group ) );
        }

        return set1;
    }

    protected Set<Group> groupDtoSetToGroupSet(Set<GroupDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Group> set1 = new LinkedHashSet<Group>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( GroupDto groupDto : set ) {
            set1.add( groupMapper.toEntity( groupDto ) );
        }

        return set1;
    }
}
