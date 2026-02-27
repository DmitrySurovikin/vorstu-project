package dev.vorstu.services;

import dev.vorstu.dto.GroupDto;
import dev.vorstu.entity.Group;
import dev.vorstu.entity.Student;
import dev.vorstu.mappers.GroupMapper;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupMapper groupMapper;

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(group -> groupMapper.toDto(group))
                .collect(Collectors.toList());
    }

    public GroupDto createGroup(GroupDto dto) {
        Group group = groupMapper.toEntity(dto);
        Group savedGroup = groupRepository.save(group);
        return groupMapper.toDto(savedGroup);
    }

    public GroupDto updateGroup(GroupDto dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("ID группы не указан");
        }
        Group group = groupMapper.toEntity(dto);
        Group savedGroup = groupRepository.save(group);
        return groupMapper.toDto(savedGroup);
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<Student> students = studentRepository.findAllByGroup(group);

        for (Student student : students) {
            student.setGroup(null);
            studentRepository.save(student);
        }

        if (group.getTeachers() != null) {
            group.getTeachers().clear();
            groupRepository.save(group);
        }

        groupRepository.deleteById(id);
    }
}
