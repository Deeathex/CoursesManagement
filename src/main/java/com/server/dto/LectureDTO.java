package com.server.dto;

import com.server.model.Course;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LectureDTO implements Serializable {
    private Long id;
    private String title;
    private String filename;
    private byte[] attachment;
    private Date date;
    private Course course;
}
