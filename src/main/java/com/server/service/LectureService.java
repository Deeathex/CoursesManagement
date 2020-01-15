package com.server.service;

import com.server.model.Course;
import com.server.model.Lecture;
import com.server.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LectureService {

    private final LectureRepository repository;

    @Autowired
    public LectureService(LectureRepository repository) {
        this.repository = repository;
    }

    public List<Lecture> getAllBy(Course course) {
        return (List<Lecture>) repository.findAllByCourse(course).orElse(new ArrayList<>());
    }

    public List<Lecture> getAllByCourseSortedByDate(Course course) {
        List<Lecture> lectures = getAllBy(course);
        lectures.sort(Comparator.comparing(Lecture::getDate));
        return lectures;
    }

    /***
     * This method will persist the new lecture into DB if the provided lecture does not exists,
     * or will update the existing one if the lecture already exists in DB.
     * @param lecture is the entity for which to be looking for in DB
     */
    public Lecture save(Lecture lecture) {
        return repository.save(lecture);
    }

    public void delete(Lecture lecture) {
        repository.delete(lecture);
    }
}
