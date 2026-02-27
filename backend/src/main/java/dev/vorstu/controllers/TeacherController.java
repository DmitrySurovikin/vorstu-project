package dev.vorstu.controllers;

import dev.vorstu.dto.GroupDto;
import dev.vorstu.dto.TeacherDto;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/teachers")
@PreAuthorize("hasAuthority('ADMIN')")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<TeacherDto> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping
    public TeacherDto createTeacher(@RequestBody @Valid TeacherDto dto) {
        return teacherService.createTeacher(dto);
    }

    @PutMapping
    public TeacherDto updateTeacher(@RequestBody @Valid TeacherDto dto) {
        return teacherService.updateTeacher(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

    @PostMapping("/{teacherId}/groups")
    public TeacherDto addGroupToTeacher(
            @PathVariable Long teacherId,
            @RequestBody GroupDto groupDto
    ) {
        return teacherService.addGroupToTeacher(teacherId, groupDto.getId());
    }
}
