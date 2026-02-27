package dev.vorstu.services;

import dev.vorstu.dto.GroupDto;
import dev.vorstu.dto.TeacherDto;
import dev.vorstu.entity.Group;
import dev.vorstu.entity.Teacher;
import dev.vorstu.entity.User;
import dev.vorstu.mappers.GroupMapper;
import dev.vorstu.mappers.TeacherMapper;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.TeacherRepository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired private TeacherRepository teacherRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TeacherMapper teacherMapper;
    @Autowired private GroupMapper groupMapper;

    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }

    public TeacherDto createTeacher(TeacherDto dto) {
        Teacher teacher = teacherMapper.toEntity(dto);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherDto updateTeacher(TeacherDto dto) {
        if (dto.getId() == null) throw new RuntimeException("ID не указан");
        Teacher teacher = teacherMapper.toEntity(dto);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public void deleteTeacher(Long id) {
        Teacher teacherToDelete = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Преподаватель не найден"));

        for (Group group : teacherToDelete.getGroups()) {
            group.getTeachers().remove(teacherToDelete);
            groupRepository.save(group);
        }
        teacherToDelete.getGroups().clear();
        teacherRepository.save(teacherToDelete);

        User user = teacherToDelete.getUser();
        if (user != null) {
            userRepository.delete(user);
        } else {
            teacherRepository.delete(teacherToDelete);
        }
    }

    public TeacherDto addGroupToTeacher(Long teacherId, Long groupId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Преподаватель не найден"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Группа не найдена"));

        teacher.getGroups().add(group);
        group.getTeachers().add(teacher);

        groupRepository.save(group);

        return teacherMapper.toDto(teacher);
    }
}