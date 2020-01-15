package com.server.dto;

import com.server.model.Lecture;
import com.server.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDTO {
    private String title;
    private String description;
    private String year;
    private Collection<Lecture> lectures = new ArrayList<>();
    private Collection<User> users = new ArrayList<>();
}
