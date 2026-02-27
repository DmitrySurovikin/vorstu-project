package dev.vorstu.mappers;

import dev.vorstu.dto.GroupDto;
import dev.vorstu.entity.Group;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-21T16:55:10+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Amazon.com Inc.)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupDto toDto(Group entity) {
        if ( entity == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setId( entity.getId() );
        groupDto.setName( entity.getName() );

        return groupDto;
    }

    @Override
    public Group toEntity(GroupDto dto) {
        if ( dto == null ) {
            return null;
        }

        Group group = new Group();

        group.setId( dto.getId() );
        group.setName( dto.getName() );

        return group;
    }
}
