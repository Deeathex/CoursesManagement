package com.server.dto.mapper;

import com.server.dto.LectureDTO;
import com.server.model.Lecture;

import java.sql.Date;

public class LectureMapper {

    public Lecture lectureDTOTOLecture(LectureDTO lectureDTO) {
        Lecture lecture = new Lecture();

        lecture.setTitle(lectureDTO.getTitle());
        lecture.setCourse(lectureDTO.getCourse());
        lecture.setDate((Date) lectureDTO.getDate());
        lecture.setFilename(lectureDTO.getFilename());
        lecture.setAttachment(lectureDTO.getAttachment());

        return lecture;
    }

    public LectureDTO lectureToLectureDTO(Lecture lecture) {
        LectureDTO lectureDTO = new LectureDTO();

        lectureDTO.setTitle(lecture.getTitle());
        lectureDTO.setCourse(lecture.getCourse());
        lectureDTO.setDate(lecture.getDate());
        lectureDTO.setFilename(lecture.getFilename());
        lectureDTO.setAttachment(lecture.getAttachment());

        return lectureDTO;
    }
}
