package server.service;

import com.server.model.Course;
import com.server.model.Lecture;
import com.server.model.User;
import com.server.repository.CourseRepository;
import com.server.repository.LectureRepository;
import com.server.service.LectureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceTest {

    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    private List<Lecture> inMemoryLectureRepository = new ArrayList<>();

    private Lecture lecture = Utils.setUpLecture();

    private Lecture lectureToSave = Utils.setUpLectureToSave();

    @Mock
    private CourseRepository courseRepository;

    private Course course = Utils.setUpCourse();

    private User professor = Utils.setUpProfessor();

    private User student = Utils.setUpStudent();

    @Before
    public void init() {
        lectureService = new LectureService(lectureRepository, courseRepository);

        inMemoryLectureRepository.add(lecture);

        when(lectureRepository.findAllByCourse(course)).thenAnswer(invocation -> {
            List<Lecture> lectures = new ArrayList<>();
            for (Lecture l : inMemoryLectureRepository) {
                if (course.equals(l.getCourse())) {
                    lectures.add(l);
                }
            }
            return Optional.of(lectures);
        });

        when(lectureRepository.save(lectureToSave)).thenAnswer(invocation -> {
            inMemoryLectureRepository.add(lectureToSave);
            return lectureToSave;
        });

        when(courseRepository.getOne(course.getId())).thenReturn(course);
    }

    @Test
    public void lectureMethodsTest() {
        assertEquals(1, inMemoryLectureRepository.size());
        Lecture lectureSaved = lectureService.save(lectureToSave, course.getId(), professor);
        assertEquals(lectureToSave, lectureSaved);
        assertEquals(2, inMemoryLectureRepository.size());

        List<Lecture> lectures = lectureService.getAllBy(course);
        assertEquals(2, lectures.size());

        List<Lecture> filteredLectures = lectureService.filterBy(course.getId(), "date");
        assertEquals(1, filteredLectures.size());
        assertEquals(lecture, filteredLectures.get(0));

        assertFalse(lectureService.delete(lectureToSave.getId(), student));
        assertTrue(lectureService.delete(lectureToSave.getId(), professor));
    }
}
