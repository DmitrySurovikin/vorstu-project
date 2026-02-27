package dev.vorstu.controllers;

import dev.vorstu.dto.StudentDto;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/base/students")
public class StudentController {

    @Autowired private StudentService studentService;

    @GetMapping
    public Page<StudentDto> getAllStudents(
            Pageable pageable,
            @RequestParam(required = false) String fio,
            Principal principal
    ) {
        return studentService.getAllStudents(principal.getName(), pageable, fio);
    }

    @PostMapping
    public StudentDto createStudent(@RequestBody @Valid StudentDto dto) {
        return studentService.createStudent(dto);
    }

    @PutMapping
    public StudentDto updateStudent(@RequestBody @Valid StudentDto dto, Principal principal) {
        return studentService.updateStudent(dto, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id, Principal principal) {
        studentService.deleteStudent(id, principal.getName());
    }
}