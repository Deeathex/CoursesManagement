package com.server.repository;

import com.server.model.Course;
import com.server.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    Optional<Collection<Lecture>> findAllByCourse(Course course);
}
