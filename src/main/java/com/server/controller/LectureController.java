package com.server.controller;

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

    private final LectureMapper lectureMapper;

    public LectureController(LectureService lectureService, UserService userService) {
        this.lectureService = lectureService;
        this.userService = userService;
        this.lectureMapper = new LectureMapper();
    }

    @GetMapping("/lectures/{course-id}")
    public ResponseEntity<?> getAllLecturesByCourse(
            @PathVariable("course-id") Long courseId,
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        List<Lecture> lectures;
        try {
            lectures = lectureService.getAllByCourseSortedByDate(user.getId(), courseId);
        } catch (Exception e) {
            return new ResponseEntity<>(Utils.getErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(lectureMapper.lecturesToLecturesDTO(lectures), HttpStatus.OK);
    }
}
