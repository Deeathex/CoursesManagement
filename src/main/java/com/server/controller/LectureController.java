package com.server.controller;

import com.server.dto.LectureDTO;
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

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ResponseEntity<?> getAllLecturesByCourse(
            @PathVariable("course-id") Long courseId,
            HttpSession session) {

        if (Utils.isNotValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        List<Lecture> lectures;
        try {
            lectures = lectureService.getAllByCourseSortedByDate(user.getId(), courseId);
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        LOG.info("User requests all lectures from course: {}", courseId);
        return new ResponseEntity<>(LectureMapper.lecturesToLecturesDTO(lectures), HttpStatus.OK);
    }

    @GetMapping("/lectures/filter")
    public ResponseEntity<?> filterLecturesBy(@RequestParam String filter, HttpSession session) {
        if (Utils.isNotValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(LectureMapper.lecturesToLecturesDTO(lectureService.filterBy(filter)), HttpStatus.OK);
    }


    @PostMapping("/lectures")
    public ResponseEntity<?> addOrEditLectureToCourse(
            @RequestBody LectureDTO lectureDTO,
            @RequestParam Long courseId,
            HttpSession session) {

        if (Utils.isNotValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Lecture lecture = LectureMapper.lectureDTOTOLecture(lectureDTO);

        try {
            lectureService.save(lecture, courseId, userService.getUserFromSession(session));
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        LOG.info("Professor performs add/edit on lecture for course with id: {}", courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/lectures/{lecture-id}")
    public ResponseEntity<?> deleteLecture(
            @PathVariable("lecture-id") Long lectureId,
            HttpSession session) {

        if (Utils.isNotValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!lectureService.delete(lectureId, userService.getUserFromSession(session))) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect lecture to delete."), HttpStatus.BAD_REQUEST);
        }

        LOG.info("Professors deletes lecture with id: {}", lectureId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
