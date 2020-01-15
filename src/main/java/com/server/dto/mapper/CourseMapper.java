package com.server.dto.mapper;

import com.server.dto.CourseDTO;
import com.server.model.Course;

@SuppressWarnings("Duplicates")
public class CourseMapper {

    public Course courseDTOToCourse(CourseDTO courseDTO) {
        Course course = new Course();

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setYear(courseDTO.getYear());
        course.setLectures(courseDTO.getLectures());
        course.setUsers(courseDTO.getUsers());

        return course;
    }

    public CourseDTO courseToCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setYear(course.getYear());
        courseDTO.setLectures(course.getLectures());
        courseDTO.setUsers(course.getUsers());

        return courseDTO;
    }
}
