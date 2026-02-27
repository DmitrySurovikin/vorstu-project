package dev.vorstu;

import dev.vorstu.entity.*;

import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.HashSet;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public void run(String... args) throws Exception {

        Group groupISP = groupRepository.save(new Group("ИСП"));
        Group groupSZS = groupRepository.save(new Group("СЗС"));
        Group groupAD  = groupRepository.save(new Group("АД"));
        Group groupDIZ = groupRepository.save(new Group("ДИЗ"));

        // тут пока 1 студент только с профилем, "Женька"
        Student studentEvgeniy = studentRepository.save(new Student("Вурдалак Евгений", groupISP, "+79101102243"));
        studentRepository.save(new Student("Молодец Иван", groupSZS, "+79514645266"));
        studentRepository.save(new Student("Кавказ Алексей", groupAD, "+79007878932"));
        studentRepository.save(new Student("Копчик Артём", groupISP, "+79102124573"));
        studentRepository.save(new Student("Творец Максим", groupDIZ, "+79518552134"));
        studentRepository.save(new Student("Каран Артур", groupAD, "+79102802474"));
        studentRepository.save(new Student("Качерга Илья", groupISP, "+79102267290"));

        User evgeniyUser = new User(
                null,
                "evgeniy",
                Role.STUDENT,
                new Password("1234"),
                true,
                studentEvgeniy,
                null
        );
        userRepository.save(evgeniyUser);

        studentEvgeniy.setUser(evgeniyUser);
        studentRepository.save(studentEvgeniy);

        Teacher teacherGrach = new Teacher("Грач Семён Зайцевич");

        User grachUser = new User(
                null,
                "grach",
                Role.TEACHER,
                new Password("qwerty"),
                true,
                null,
                teacherGrach
        );
        userRepository.save(grachUser);
        teacherGrach.setUser(grachUser);

        // инициализируем коллекшн
        if (teacherGrach.getGroups() == null) teacherGrach.setGroups(new java.util.HashSet<>());
        if (groupISP.getTeachers() == null) groupISP.setTeachers(new java.util.HashSet<>());

        teacherGrach.getGroups().add(groupISP);
        groupISP.getTeachers().add(teacherGrach);

        // сохр group
        groupRepository.save(groupISP);

        User adminUser = new User(
                null,
                "admin",
                Role.ADMIN,
                new Password("admin"),
                true,
                null,
                null
        );
        userRepository.save(adminUser);

    }

}
