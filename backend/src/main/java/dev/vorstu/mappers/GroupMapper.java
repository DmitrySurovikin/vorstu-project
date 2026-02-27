package dev.vorstu.mappers;

import dev.vorstu.dto.GroupDto;
import dev.vorstu.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto(Group entity);
    Group toEntity(GroupDto dto);
}
