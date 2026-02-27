package dev.vorstu.repositories;

import dev.vorstu.entity.Student;
import dev.vorstu.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByFioContainingIgnoreCase(String fio, Pageable pageable);

    Page<Student> findAllByGroup(Group group, Pageable pageable);

    List<Student> findAllByGroup(Group group);

    Page<Student> findAllByGroupIn(Collection<Group> groups, Pageable pageable);
}
