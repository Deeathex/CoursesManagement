package com.server.dto.mapper;

import com.server.dto.CourseDTO;
import com.server.dto.LectureDTO;
import com.server.dto.UserDTO;
import com.server.model.Course;
import com.server.model.Lecture;
import com.server.model.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class CourseMapper {

    public Course courseDTOToCourse(CourseDTO courseDTO) {
        Course course = new Course();

        course.setId(courseDTO.getId());
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setYear(courseDTO.getYear());
        course.setMaxStudents(courseDTO.getMaxStudents());

        List<Lecture> lectures = new ArrayList<>();
        for (LectureDTO lectureDTO : courseDTO.getLectures()) {
            Lecture lecture = new Lecture();
            lecture.setId(lectureDTO.getId());
            lecture.setTitle(lectureDTO.getTitle());
            lecture.setDate((Date) lectureDTO.getDate());
            lecture.setFilename(lectureDTO.getFilename());
            lecture.setAttachment(lectureDTO.getAttachment());
            lectures.add(lecture);
        }
        course.setLectures(lectures);

        UserMapper userMapper = new UserMapper();
        course.setUsers(userMapper.usersDTOToUsers((List<UserDTO>) courseDTO.getUsers()));

        return course;
    }

    public CourseDTO courseToCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setYear(course.getYear());
        courseDTO.setMaxStudents(course.getMaxStudents());

        List<LectureDTO> lectureDTOS = new ArrayList<>();
        for (Lecture lecture : course.getLectures()) {
            LectureDTO lectureDTO = new LectureDTO();
            lectureDTO.setId(lecture.getId());
            lectureDTO.setTitle(lecture.getTitle());
            lectureDTO.setDate(lecture.getDate());
            lectureDTO.setFilename(lecture.getFilename());
            lectureDTO.setAttachment(lecture.getAttachment());
            lectureDTOS.add(lectureDTO);
        }
        courseDTO.setLectures(lectureDTOS);

        UserMapper userMapper = new UserMapper();
        courseDTO.setUsers(userMapper.usersToUsersDTO((List<User>) course.getUsers()));

        return courseDTO;
    }

    public List<CourseDTO> coursesToCoursesDTO(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();

        for (Course user : courses) {
            coursesDTO.add(courseToCourseDTO(user));
        }

        return coursesDTO;
    }

    public List<Course> coursesDTOToCourses(List<CourseDTO> coursesDTO) {
        List<Course> courses = new ArrayList<>();

        for (CourseDTO userDTO : coursesDTO) {
            courses.add(courseDTOToCourse(userDTO));
        }

        return courses;
    }
}
