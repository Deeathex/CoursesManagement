package com.server.dto.mapper;

import com.server.dto.LectureDTO;
import com.server.model.Lecture;

import java.util.ArrayList;
import java.util.List;

public class LectureMapper {

    public static Lecture lectureDTOTOLecture(LectureDTO lectureDTO) {
        Lecture lecture = new Lecture();

        lecture.setId(lectureDTO.getId());
        lecture.setTitle(lectureDTO.getTitle());
        lecture.setDate(lectureDTO.getDate());
        lecture.setFilename(lectureDTO.getFilename());
        lecture.setAttachment(lectureDTO.getAttachment());

        return lecture;
    }

    public static LectureDTO lectureToLectureDTO(Lecture lecture) {
        LectureDTO lectureDTO = new LectureDTO();

        lectureDTO.setId(lecture.getId());
        lectureDTO.setTitle(lecture.getTitle());
        lectureDTO.setDate(lecture.getDate());
        lectureDTO.setFilename(lecture.getFilename());
        lectureDTO.setAttachment(lecture.getAttachment());

        return lectureDTO;
    }

    public static List<LectureDTO> lecturesToLecturesDTO(List<Lecture> lectures) {
        List<LectureDTO> lecturesDTO = new ArrayList<>();

        for (Lecture user : lectures) {
            lecturesDTO.add(lectureToLectureDTO(user));
        }

        return lecturesDTO;
    }
}
