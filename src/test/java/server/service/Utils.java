package server.service;

import com.server.model.Course;
import com.server.model.Lecture;
import com.server.model.User;
import com.server.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String PROFESSOR_NAME = "Ioan";
    public static final String PROFESSOR_SURNAME = "Pop";
    public static final String PROFESSOR_EMAIL = "profesor@cs.ubbcluj.ro";

    public static final String STUDENT_NAME = "Gigel";
    public static final String STUDENT_SURNAME = "Frasinariu";
    public static final String STUDENT_EMAIL = "student@scs.ubbcluj.ro";

    public static final String PASSWORD = "password";

    public static final String STUDENT_TO_SAVE_EMAIL = "casd1924@scs.ubbcluj.ro";

    public static final String EMAIL_NOT_SUPPORTED = "test1234@yahoo.com";
    public static final String YEAR = "2020";
    public static final String COURSE_TITLE = "Fluxuri de date";
    public static final String COURSE_DESCRIPTION = "Operatiuni pe fluxuri de date.";
    public static final String TPJAD = "TPJAD";

    public static final String LECTURE_TITLE = "Baze de date distribuite";
    public static final String LECTURE_TO_SAVE_TITLE = "Hibernate";

    public static User setUpStudent() {
        User student = new User();

        student.setId(2L);
        student.setRole(Role.STUDENT);
        student.setEmail(STUDENT_EMAIL);
        student.setPassword(PASSWORD);
        student.setName(STUDENT_NAME);
        student.setSurname(STUDENT_SURNAME);

        return student;
    }

    public static User setUpProfessor() {
        User professor = new User();

        professor.setId(1L);
        professor.setRole(Role.PROFESSOR);
        professor.setEmail(PROFESSOR_EMAIL);
        professor.setPassword(PASSWORD);
        professor.setName(PROFESSOR_NAME);
        professor.setSurname(PROFESSOR_SURNAME);

        return professor;
    }

    public static User setUpUserToSave() {
        User studentToSave = new User();

        studentToSave.setEmail(STUDENT_TO_SAVE_EMAIL);
        studentToSave.setPassword(PASSWORD);

        return studentToSave;
    }

    public static Course setUpCourse(List<User> users) {
        Course course = new Course();

        course.setId(1L);
        course.setMaxStudents(50L);
        course.setYear(YEAR);
        course.setTitle(COURSE_TITLE);
        course.setDescription(COURSE_DESCRIPTION);
        course.setUsers(users);

        return course;
    }

    public static Course setUpCourse() {
        Course course = new Course();

        course.setId(1L);
        course.setMaxStudents(50L);
        course.setYear(YEAR);
        course.setTitle(COURSE_TITLE);
        course.setDescription(COURSE_DESCRIPTION);

        return course;
    }

    public static Course setUpCourseToSave() {
        Course courseToSave = new Course();

        courseToSave.setId(2L);
        courseToSave.setMaxStudents(50L);
        courseToSave.setYear(YEAR);
        courseToSave.setTitle(TPJAD);

        return courseToSave;
    }

    public static Lecture setUpLecture() {
        Lecture lecture = new Lecture();

        lecture.setId(1L);
        lecture.setTitle(LECTURE_TITLE);
        lecture.setCourse(setUpCourse());

        return lecture;
    }

    public static Lecture setUpLectureToSave() {
        Lecture lecture = new Lecture();

        lecture.setId(2L);
        lecture.setTitle(LECTURE_TO_SAVE_TITLE);
        lecture.setCourse(setUpCourseToSave());

        return lecture;
    }
}
