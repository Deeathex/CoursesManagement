package com.server.service;

import com.server.model.Course;
import com.server.model.Lecture;
import com.server.model.enums.Role;
import com.server.repository.CourseRepository;
import com.server.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    private final CourseRepository courseRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
    }

    public List<Lecture> getAllBy(Course course) {
        return (List<Lecture>) lectureRepository.findAllByCourse(course).orElse(new ArrayList<>());
    }

    /***
     * This method gets all the lectures that correspond to the given course, by also
     * checking the user's permissions to see the lectures.
     * @param userId the id of the user that is requesting the lectures
     * @param courseId the id of the course for which to look for the lectures
     * @return the list with all the lectures (sorted by date) corresponding to userId, courseId
     */
    public List<Lecture> getAllByCourseSortedByDate(Long userId, Long courseId) {
        Course course = courseRepository.getOne(courseId);
        if (course.getUsers().stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new RuntimeException("User not enrolled to the course!");
        }

        return getAllBy(course)
                .stream()
                .sorted(Comparator.comparing(Lecture::getDate))
                .collect(Collectors.toList());

    }

    /***
     * This method will persist the new lecture into DB if the provided lecture does not exists,
     * or will update the existing one if the lecture already exists in DB.
     * @param lecture is the entity for which to be looking for in DB
     */
    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public void delete(Lecture lecture) {
        lectureRepository.delete(lecture);
    }
}
