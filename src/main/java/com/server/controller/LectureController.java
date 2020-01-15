package com.server.controller;

import com.server.dto.mapper.UserMapper;
import com.server.service.LectureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "lectures-management")
public class LectureController {
    private static final Logger LOG = LogManager.getLogger(UserController.class.getName());

    private final LectureService lectureService;
    private UserMapper userMapper;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }
}
