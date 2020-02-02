package com.server.controller;

import com.server.dto.LectureDTOWrapper;
import com.server.dto.mapper.LectureMapper;
import com.server.model.Lecture;
import com.server.model.User;
import com.server.service.LectureService;
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
@RequestMapping(value = "lectures-management")
public class LectureController {
    private static final Logger LOG = LogManager.getLogger(UserController.class.getName());

    private final LectureService lectureService;
    private final UserService userService;

    public LectureController(LectureService lectureService, UserService userService) {
        this.lectureService = lectureService;
        this.userService = userService;
    }

    @GetMapping("/lectures/{course-id}")
    public ResponseEntity<?> getAllLecturesByCourse(@PathVariable("course-id") Long courseId) {
        User user = Utils.getUserFromHeader(userService);

        LOG.info("User " + user.getEmail() + " requests all lectures from course: {}", courseId);

        List<Lecture> lectures;
        try {
            lectures = lectureService.getAllByCourseSortedByDate(user.getId(), courseId);
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(LectureMapper.lecturesToLecturesDTO(lectures), HttpStatus.OK);
    }

    @GetMapping("/lectures/filter/{course-id}")
    public ResponseEntity<?> filterLecturesBy(@RequestParam String filter, @PathVariable("course-id") Long courseId) {
        return new ResponseEntity<>(LectureMapper.lecturesToLecturesDTO(lectureService.filterBy(courseId, filter)), HttpStatus.OK);
    }


    @PostMapping("/lectures")
    public ResponseEntity<?> addOrEditLectureToCourse(@RequestBody LectureDTOWrapper lectureDTOWrapper) {
        User user = Utils.getUserFromHeader(userService);

        Lecture lecture = LectureMapper.lectureDTOTOLecture(lectureDTOWrapper.getLectureDTO());
        Long courseId = lectureDTOWrapper.getCourseId();

        try {
            lectureService.save(lecture, courseId, user);
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        LOG.info("Professor performs add/edit on lecture for course with id: {}", courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/lectures/{lecture-id}")
    public ResponseEntity<?> deleteLecture(@PathVariable("lecture-id") Long lectureId) {
        User user = Utils.getUserFromHeader(userService);

        if (!lectureService.delete(lectureId, user)) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect lecture to delete."), HttpStatus.BAD_REQUEST);
        }

        LOG.info("Professors deletes lecture with id: {}", lectureId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
