package com.server.dto;

import com.server.model.Lecture;
import com.server.model.User;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String year;
    private Long maxStudents;
    private Collection<LectureDTO> lectures = new ArrayList<>();
    private Collection<UserDTO> users = new ArrayList<>();
}
