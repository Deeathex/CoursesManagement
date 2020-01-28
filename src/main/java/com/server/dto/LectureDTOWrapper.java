package com.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LectureDTOWrapper implements Serializable {
    Long courseId;
    LectureDTO lectureDTO;
}
