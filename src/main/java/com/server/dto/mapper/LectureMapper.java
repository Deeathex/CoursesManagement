package com.server.dto.mapper;

import com.server.dto.LectureDTO;
import com.server.dto.UserDTO;
import com.server.model.Lecture;
import com.server.model.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LectureMapper {

    public Lecture lectureDTOTOLecture(LectureDTO lectureDTO) {
        Lecture lecture = new Lecture();

        lecture.setId(lectureDTO.getId());
        lecture.setTitle(lectureDTO.getTitle());
        lecture.setCourse(lectureDTO.getCourse());
        lecture.setDate((Date) lectureDTO.getDate());
        lecture.setFilename(lectureDTO.getFilename());
        lecture.setAttachment(lectureDTO.getAttachment());

        return lecture;
    }

    public LectureDTO lectureToLectureDTO(Lecture lecture) {
        LectureDTO lectureDTO = new LectureDTO();

        lectureDTO.setId(lecture.getId());
        lectureDTO.setTitle(lecture.getTitle());
        lectureDTO.setCourse(lecture.getCourse());
        lectureDTO.setDate(lecture.getDate());
        lectureDTO.setFilename(lecture.getFilename());
        lectureDTO.setAttachment(lecture.getAttachment());

        return lectureDTO;
    }

    public List<LectureDTO> lecturesToLecturesDTO(List<Lecture> lectures) {
        List<LectureDTO> lecturesDTO = new ArrayList<>();

        for (Lecture user : lectures) {
            lecturesDTO.add(lectureToLectureDTO(user));
        }

        return lecturesDTO;
    }

    public List<Lecture> lecturesDTOToLectures(List<LectureDTO> lecturesDTO) {
        List<Lecture> lectures = new ArrayList<>();

        for (LectureDTO userDTO : lecturesDTO) {
            lectures.add(lectureDTOTOLecture(userDTO));
        }

        return lectures;
    }
}
