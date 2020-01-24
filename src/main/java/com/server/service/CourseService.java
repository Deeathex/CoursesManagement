package com.server.service;

import com.server.model.Course;
import com.server.model.User;
import com.server.model.enums.Role;
import com.server.repository.CourseRepository;
import com.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<Course> getAllBy(User user) {
        List<User> users = new ArrayList<>();
        users.add(user);
        return (List<Course>) courseRepository.findAllByUsersOrderByYearDesc(users).orElse(new ArrayList<>());
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    /***
     * For My courses page
     * @param userId the user for which to bring the courses
     * @return all the courses for the user with the provided id.
     */
    public List<Course> getAllBy(Long userId) {
        User user = userRepository.getOne(userId);
        return getAllBy(user);
    }

    /***
     * This method will offer much more details about a course.
     * @param courseId the id of the course
     * @param userId the user that requests the course
     * @return the course for the provided id
     */
    public Course getCourseDetails(Long courseId, Long userId) {
        if (checkForUserWithRole(userId, Role.PROFESSOR) == null) {
            throw new RuntimeException("The provided user does not have permissions.");
        }

        return courseRepository.getOne(courseId);
    }

    /***
     * This method will persist the new course into DB if the provided course does not exists,
     * or will update the existing one if the course already exists in DB.
     * @param course is the entity for which to be looking for in DB
     * @param user is the user that wants to perform the save (only professors are allowed to do that)
     */
    public Course save(Course course, User user) {
        if (checkForUserWithRole(user.getId(), Role.PROFESSOR) == null) {
            throw new RuntimeException("The provided user does not have permissions.");
        }

        // the professor must be added too in the list of users within a course
        user.getCourses().add(course);
        userRepository.save(user);

        return courseRepository.save(course);
    }

    public boolean delete(Long courseId, Long userId) {
        if (checkForUserWithRole(userId, Role.PROFESSOR) == null) {
            return false;
        }

        courseRepository.deleteById(courseId);
        return true;
    }

    public boolean enrollStudent(Long courseId, Long userId) {
        try {
            User user = checkForUserWithRole(userId, Role.STUDENT);
            if (user == null) {
                return false;
            }
            Course newCourse = courseRepository.getOne(courseId);
            user.getCourses().add(newCourse);
            userRepository.save(user);

            return true;
        } catch (javax.persistence.EntityNotFoundException e) {
            return false;
        }
    }

    public List<Course> filterBy(String filter) {
        Predicate<Course> mainPredicate = x -> false;
        mainPredicate = mainPredicate
                .or(x -> x.getDescription().contains(filter))
                .or(x -> x.getTitle().contains(filter))
                .or(x -> x.getYear().contains(filter))
                .or(x -> x.getLectures().stream().anyMatch(y -> y.getTitle().contains(filter)));

        return courseRepository.findAll()
                .stream()
                .filter(mainPredicate)
                .collect(Collectors.toList());
    }

    private User checkForUserWithRole(Long userId, Role role) {
        User user = userRepository.getOne(userId);
        if (user.getRole() == role) {
            return user;
        }

        return null;
    }

    public int getNumberOfStudentsFromCourse(Long courseId, Long userId) {
        User user = checkForUserWithRole(userId, Role.PROFESSOR);
        if (user == null) {
            throw new RuntimeException("Incorrect courseId or userId.");
        }

        Course course = courseRepository.getOne(courseId);

        int studentsCount = 0;
        for (User student : course.getUsers()) {
            if (student.getRole().equals(Role.STUDENT)) {
                studentsCount++;
            }
        }

        return studentsCount;
    }
}
