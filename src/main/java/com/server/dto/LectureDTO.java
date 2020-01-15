package com.server.dto;

import com.server.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LectureDTO {
    private String title;
    private String filename;
    private byte[] attachment;
    private Date date;
    private Course course;
}
