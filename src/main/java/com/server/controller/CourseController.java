package com.server.controller;

import com.server.dto.CourseDTO;
import com.server.dto.NewsDTO;
import com.server.dto.mapper.CourseMapper;
import com.server.dto.mapper.UserMapper;
import com.server.model.Course;
import com.server.model.User;
import com.server.service.CourseService;
import com.server.service.EmailService;
import com.server.service.UserService;
import com.server.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "courses-management")
public class CourseController {
    private static final Logger LOG = LogManager.getLogger(CourseController.class.getName());

    private final CourseService courseService;
    private final UserService userService;
    private final EmailService emailService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
        this.emailService = new EmailService();
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(@RequestHeader("session-id") String sessionId) {
        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOG.info("User requests all courses.");
        return new ResponseEntity<>(CourseMapper.coursesToCoursesDTO(courseService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/courses/my-courses")
    public ResponseEntity<?> getAllMyCourses(@RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOG.info("User requests all his courses.");
        return new ResponseEntity<>(CourseMapper.coursesToCoursesDTO(courseService.getAllBy(userService.getUserFromSession(Utils.getSession(sessionId)))), HttpStatus.OK);
    }

    @GetMapping("/courses/{course-id}")
    public ResponseEntity<?> getCourseDetailsById(
            @PathVariable("course-id") Long courseId,
            @RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Course course;
        try {
            course = courseService.getCourseDetails(courseId);
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        LOG.info("User wants to see details for course: {}", course);
        return new ResponseEntity<>(CourseMapper.courseToCourseDTO(course), HttpStatus.OK);
    }

    @PostMapping("/courses/{course-id}/enroll")
    public ResponseEntity<?> enrollStudentToCourse(
            @PathVariable("course-id") Long courseId,
            @RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!courseService.enrollStudent(courseId, userService.getUserFromSession(Utils.getSession(sessionId)))) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect course."), HttpStatus.BAD_REQUEST);
        }

        LOG.info("User enrolled to course with id: {}", courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/courses/filter")
    public ResponseEntity<?> filterCoursesBy(@RequestParam String filter, @RequestHeader("session-id") String sessionId) {
        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(CourseMapper.coursesToCoursesDTO(courseService.filterBy(filter)), HttpStatus.OK);
    }


    @PostMapping("/courses")
    public ResponseEntity<?> addOrEditCourse(
            @RequestBody CourseDTO courseDTO,
            @RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Course course = CourseMapper.courseDTOToCourse(courseDTO);

        try {
            courseService.save(course, userService.getUserFromSession(Utils.getSession(sessionId)));
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        LOG.info("User performs add/edit on course: {}", courseDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/courses/{course-id}")
    public ResponseEntity<?> deleteCourse(
            @PathVariable("course-id") Long courseId,
            @RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!courseService.delete(courseId, userService.getUserFromSession(Utils.getSession(sessionId)))) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect course."), HttpStatus.BAD_REQUEST);
        }

        LOG.info("User deleted course with id: {}", courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/courses/{course-id}/students-number")
    public ResponseEntity<?> getNumberOfStudentsFromCourse(
            @PathVariable("course-id") Long courseId,
            @RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        int numberOfStudents;
        try {
            numberOfStudents = courseService.getNumberOfStudentsFromCourse(courseId, userService.getUserFromSession(Utils.getSession(sessionId)));
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect course."), HttpStatus.BAD_REQUEST);
        }

        LOG.info("User requests the number of students for course: " + courseId + " - " + numberOfStudents);
        return new ResponseEntity<>(Utils.getJsonForFrontEndAsString("studentsNumber", "" + numberOfStudents), HttpStatus.OK);
    }

    @GetMapping("/courses/{course-id}/students")
    public ResponseEntity<?> getStudentsFromCourse(
            @PathVariable("course-id") Long courseId,
            @RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<User> studentsFromCourse;
        try {
            studentsFromCourse = courseService.getStudentsFromCourse(courseId, userService.getUserFromSession(Utils.getSession(sessionId)));
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect course or not authorized."), HttpStatus.BAD_REQUEST);
        }

        LOG.info("User requests the students enrolled to course: {}", courseId);
        return new ResponseEntity<>(UserMapper.usersToUsersDTO(studentsFromCourse), HttpStatus.OK);
    }

    @PostMapping("/courses/email")
    public ResponseEntity<?> sendNewsToStudents(
            @RequestBody NewsDTO newsDTO,
            @RequestHeader("session-id") String sessionId) {

        if (Utils.isNotValid(sessionId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            List<User> studentsToBeNotified = courseService.getStudentsFromCourse(newsDTO.getCourseId(), userService.getUserFromSession(Utils.getSession(sessionId)));
            emailService.sendEmail(newsDTO.getNewsMessage(), studentsToBeNotified);
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect course."), HttpStatus.BAD_REQUEST);
        }

        LOG.info("Professor notifies students enrolled to course with id: {}", newsDTO.getCourseId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
