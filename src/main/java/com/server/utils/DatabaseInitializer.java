package com.server.utils;

import com.server.model.Course;
import com.server.model.Lecture;
import com.server.model.User;
import com.server.service.CourseService;
import com.server.service.LectureService;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private static final String PASSWORD = "password";

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LectureService lectureService;

    public void seedData() {
        // profesori
        User professor1 = new User();
        professor1.setName("Ioan");
        professor1.setSurname("Popescu");
        professor1.setEmail("popescu@cs.ubbcluj.ro");
        professor1.setPassword(PASSWORD);
        userService.register(professor1);

        User admin = new User();
        admin.setName("Admin");
        admin.setSurname("Admin");
        admin.setEmail("admin@cs.ubbcluj.ro");
        admin.setPassword("admin");
        userService.register(admin);

        User professor2 = new User();
        professor2.setName("Mihai");
        professor2.setSurname("Frasinariu");
        professor2.setEmail("frasinariu@cs.ubbcluj.ro");
        professor2.setPassword(PASSWORD);
        userService.register(professor2);

        // cursuri
        Course course1 = new Course();
        course1.setTitle("Baze de date");
        course1.setDescription("Introducere in conceptele folosite in bazele de date: baze de date relationale, tabele, proceduri si functii stocate.");
        course1.setYear("2020");
        course1.setMaxStudents(200L);
        courseService.save(course1, professor1);

        Course course2 = new Course();
        course2.setTitle("Fundamentele programarii");
        course2.setDescription("Concepte de algoritmica (backtracking, divide et impera, greedy, sortari, cautari) si introducere in limbajul Python.");
        course2.setYear("2020");
        course2.setMaxStudents(200L);
        courseService.save(course2, professor2);

        Course course3 = new Course();
        course3.setTitle("Criptografie");
        course3.setDescription("Elemente de aritmetica modulara si criptografie.");
        course3.setYear("2019");
        course3.setMaxStudents(60L);
        courseService.save(course3, admin);

        // lectures
        Lecture lecture1 = new Lecture();
        lecture1.setTitle("Baze de date realtionale");
        lecture1.setDate(LocalDate.of(2020, 1, 13));
        lectureService.save(lecture1, course1.getId(), professor1);

        Lecture lecture2 = new Lecture();
        lecture2.setTitle("Tabele");
        lecture2.setDate(LocalDate.of(2020, 1, 20));
        lectureService.save(lecture2, course1.getId(), professor1);

        Lecture lecture3 = new Lecture();
        lecture3.setTitle("Introducere in Python");
        lecture3.setDate(LocalDate.of(2019, 10, 20));
        lectureService.save(lecture3, course2.getId(), professor2);

        Lecture lecture4 = new Lecture();
        lecture4.setTitle("Cautari");
        lecture4.setDate(LocalDate.of(2019, 10, 27));
        lectureService.save(lecture4, course2.getId(), professor2);

        Lecture lecture5 = new Lecture();
        lecture5.setTitle("RSA");
        lecture5.setDate(LocalDate.of(2019, 10, 5));
        lectureService.save(lecture5, course3.getId(), admin);

        // studenti
        User student1 = new User();
        student1.setName("Andreea");
        student1.setSurname("Ciforac");
        student1.setEmail("casd1924@scs.ubbcluj.ro");
        student1.setPassword(PASSWORD);
        userService.register(student1);

        User student2 = new User();
        student2.setName("Alexandru");
        student2.setSurname("Cosarca");
        student2.setEmail("casd1927@scs.ubbcluj.ro");
        student2.setPassword(PASSWORD);
        userService.register(student2);

        User student3 = new User();
        student3.setName("Claudia");
        student3.setSurname("Coste");
        student3.setEmail("ccsd1930@scs.ubbcluj.ro");
        student3.setPassword(PASSWORD);
        userService.register(student3);

        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setName("Prenume" + i);
            user.setSurname("Nume");
            user.setEmail("email" + i + "@scs.ubbcluj.ro");
            user.setPassword(PASSWORD);
            userService.register(user);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //seedData();
    }
}
