package dev.vorstu.services;

import dev.vorstu.dto.StudentDto;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.mappers.StudentMapper;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    private User getUserByname(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Юзер нот фаунд"));
    }

    public Page<StudentDto> getAllStudents(String username, Pageable pageable, String filterFio) {
        User actor = getUserByname(username);
        if (filterFio != null && !filterFio.isEmpty()) {
            return studentRepository.findByFioContainingIgnoreCase(filterFio, pageable)
                    .map(studentMapper::toDto);
        }

        if(actor.getRole() == Role.ADMIN){
            return studentRepository.findAll(pageable)
                    .map(studentMapper::toDto);
        }

        if(actor.getRole() == Role.STUDENT){
            if(actor.getStudent() == null || actor.getStudent().getGroup() == null){
                return Page.empty();
            }
            return studentRepository.findAllByGroup(actor.getStudent().getGroup(), pageable)
                    .map(studentMapper::toDto);
        }

        if(actor.getRole() == Role.TEACHER){
            if(actor.getTeacher() == null || actor.getTeacher().getGroups().isEmpty()){
                return Page.empty();
            }
            return studentRepository.findAllByGroupIn(actor.getTeacher().getGroups(), pageable)
                    .map(studentMapper::toDto);
        }
        return Page.empty();
    }

    public StudentDto createStudent(StudentDto dto) {
        Student student = studentMapper.toEntity(dto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDto updateStudent(StudentDto dto, String username) {
        User actor = getUserByname(username);
        if(dto.getId() == null) throw new RuntimeException("Не указан ID");

        Student original = studentRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Не найден"));

        if(actor.getRole() == Role.ADMIN){
            return saveChanges(dto);
        }

        if(actor.getRole() == Role.TEACHER){
            boolean isMyGroup = actor.getTeacher().getGroups().contains(original.getGroup());
            if(!isMyGroup) throw new RuntimeException("Группа не ваша");
            return saveChanges(dto);
        }

        if(actor.getRole() == Role.STUDENT){
            if(!actor.getStudent().getId().equals(dto.getId())) {
                throw new RuntimeException("Можно менять только себя");
            }
            return saveChanges(dto);
        }

        throw new RuntimeException("Нет прав");
    }

    private StudentDto saveChanges(StudentDto dto) {
        Student student = studentMapper.toEntity(dto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public void deleteStudent(Long id, String username) {
        User actor = getUserByname(username);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));

        if(actor.getRole() == Role.ADMIN){
            studentRepository.deleteById(id);
            return;
        }

        if(actor.getRole() == Role.TEACHER){
            boolean isMyGroup = actor.getTeacher().getGroups().contains(student.getGroup());
            if (isMyGroup) throw new RuntimeException("Не ваша группа");

            studentRepository.deleteById(id);
            return;
        }

        throw new RuntimeException("Нет прав");
    }
}
