package com.server.controller;

import com.server.dto.CourseDTO;
import com.server.dto.mapper.CourseMapper;
import com.server.model.Course;
import com.server.model.User;
import com.server.service.CourseService;
import com.server.service.UserService;
import com.server.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RestController
@RequestMapping(value = "courses-management")
public class CourseController {
    private static final Logger LOG = LogManager.getLogger(CourseController.class.getName());

    private final CourseService courseService;
    private final UserService userService;

    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
        this.courseMapper = new CourseMapper();
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(HttpSession session) {
        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(courseMapper.coursesToCoursesDTO(courseService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/courses/my-courses")
    public ResponseEntity<?> getAllMyCourses(
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        return new ResponseEntity<>(courseMapper.coursesToCoursesDTO(courseService.getAllBy(user.getId())), HttpStatus.OK);
    }

    @GetMapping("/courses/{course-id}")
    public ResponseEntity<?> getCourseDetailsById(
            @PathVariable("course-id") Long courseId,
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        Course course;
        try {
            course = courseService.getCourseDetails(courseId, user.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courseMapper.courseToCourseDTO(course), HttpStatus.OK);
    }

    @PostMapping("/courses/{course-id}/enroll")
    public ResponseEntity<?> enrollStudentToCourse(
            @PathVariable("course-id") Long courseId,
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        if (!courseService.enrollStudent(courseId, user.getId())) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect user or course."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/courses/filter")
    public ResponseEntity<?> filterCoursesBy(@RequestParam String filter, HttpSession session) {
        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(courseMapper.coursesToCoursesDTO(courseService.filterBy(filter)), HttpStatus.OK);
    }


    @PostMapping("/courses")
    public ResponseEntity<?> addOrEditCourse(
            @RequestBody CourseDTO courseDTO,
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);
        Course course = courseMapper.courseDTOToCourse(courseDTO);

        try {
            courseService.save(course, user);
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/courses/{course-id}")
    public ResponseEntity<?> deleteCourse(
            @PathVariable("course-id") Long courseId,
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        if (!courseService.delete(courseId, user.getId())) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect user or course."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/courses/{course-id}/students-number")
    public ResponseEntity<?> getNumberOfStudentsFromCourse(
            @PathVariable("course-id") Long courseId,
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        int numberOfStudents = 0;
        try {
            numberOfStudents = courseService.getNumberOfStudentsFromCourse(courseId, user.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect user or course."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Utils.getJsonForFrontEndAsString("studentsNumber", "" + numberOfStudents), HttpStatus.OK);
    }
}
