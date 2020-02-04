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

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    /***
     * For My courses page
     * @param user the user for which to bring the courses
     * @return all the courses for the user with the provided id.
     */
    public List<Course> getAllBy(User user) {
        List<User> users = new ArrayList<>();
        users.add(user);
        return (List<Course>) courseRepository.findAllByUsersOrderByYearDesc(users).orElse(new ArrayList<>());
    }

    /***
     * This method will offer much more details about a course.
     * @param courseId the id of the course
     * @return the course for the provided id
     */
    public Course getCourseDetails(Long courseId) {
        return courseRepository.getOne(courseId);
    }

    /***
     * This method will persist the new course into DB if the provided course does not exists,
     * or will update the existing one if the course already exists in DB.
     * @param course is the entity for which to be looking for in DB
     * @param user is the user that wants to perform the save (only professors are allowed to do that)
     */
    public Course save(Course course, User user) {
        if (!user.getRole().equals(Role.PROFESSOR)) {
            throw new RuntimeException("Not authorized.");
        }

        // the professor must be added too in the list of users within a course
        user.getCourses().add(course);
        //userRepository.save(user);

        return courseRepository.save(course);
    }

    public boolean delete(Long courseId, User user) {
        if (!user.getRole().equals(Role.PROFESSOR)) {
            return false;
        }

        courseRepository.deleteById(courseId);
        return true;
    }

    public boolean enrollStudent(Long courseId, User user) {
        try {
            if (!user.getRole().equals(Role.STUDENT)) {
                return false;
            }

            int numberOfStudents = getNumberOfStudentsFromCourse(courseId);
            Course newCourse = courseRepository.getOne(courseId);
            if (numberOfStudents < newCourse.getMaxStudents()) {
                user.getCourses().add(newCourse);
                userRepository.save(user);
                return true;
            }

        } catch (javax.persistence.EntityNotFoundException e) {
            return false;
        }
        return false;
    }

    public List<Course> filterBy(String filter) {
        Predicate<Course> mainPredicate = x -> false;
        mainPredicate = mainPredicate
                .or(x -> x.getDescription() != null && x.getDescription().contains(filter))
                .or(x -> x.getTitle() != null && x.getTitle().contains(filter))
                .or(x -> x.getYear() != null && x.getYear().contains(filter))
                .or(x -> x.getLectures() != null && x.getLectures().stream().anyMatch(y -> y.getTitle().contains(filter)));

        return courseRepository.findAll()
                .stream()
                .filter(mainPredicate)
                .collect(Collectors.toList());
    }

    public int getNumberOfStudentsFromCourse(Long courseId) {
        Course course = courseRepository.getOne(courseId);

        int studentsCount = 0;
        for (User student : course.getUsers()) {
            if (student.getRole().equals(Role.STUDENT)) {
                studentsCount++;
            }
        }

        return studentsCount;
    }

    public List<User> getStudentsFromCourse(Long courseId, User user) {
        if (!user.getRole().equals(Role.PROFESSOR)) {
            throw new RuntimeException("Not authorized.");
        }

        try {
            return courseRepository.getOne(courseId)
                    .getUsers()
                    .stream()
                    .filter(u -> u.getRole().equals(Role.STUDENT))
                    .collect(Collectors.toList());
        } catch (Exception ignored) {

        }

        return new ArrayList<>();
    }
}
