package server.service;

import com.server.model.Course;
import com.server.model.User;
import com.server.repository.CourseRepository;
import com.server.repository.UserRepository;
import com.server.service.CourseService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static server.service.Utils.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    private List<Course> inMemoryCourseRepository = new ArrayList<>();

    private Course course = new Course();
    private Course courseToSave = new Course();

    @Mock
    private UserRepository userRepository;

    private List<User> inMemoryUserRepository = new ArrayList<>();


    private User professor = new User();
    private User student = new User();

    @Before
    public void init() {
        courseService = new CourseService(courseRepository, userRepository);

        professor = setUpProfessor();
        when(userRepository.save(professor)).thenAnswer(invocation -> {
            inMemoryUserRepository.add(professor);
            return professor;
        });

        course = setUpCourse(inMemoryUserRepository);
        inMemoryCourseRepository.add(course);

        when(courseRepository.findAll()).thenReturn(inMemoryCourseRepository);
        when(courseRepository.findAllByUsersOrderByYearDesc(Collections.singletonList(student)))
                .thenReturn(Optional.of(Collections.singletonList(course)));

        courseToSave = setUpCourseToSave();
        when(courseRepository.save(courseToSave)).thenAnswer(invocation -> {
            inMemoryCourseRepository.add(courseToSave);
            return courseToSave;
        });
        doAnswer((Answer<Void>) invocation -> {
            inMemoryCourseRepository.remove(courseToSave);
            return null;
        }).when(courseRepository).deleteById(2L);
    }

    @Test
    public void getAllTest() {
        List<Course> courses = courseService.getAll();
        assertEquals(1, courses.size());

        assertEquals(COURSE_TITLE, courses.get(0).getTitle());
    }

    @Test
    public void getAllByUser() {
        List<Course> studentCourses = courseService.getAllBy(student);
        assertEquals(1, studentCourses.size());
        assertEquals(COURSE_TITLE, studentCourses.get(0).getTitle());
    }

    @Test
    public void saveTest() {
        Course returnedCourse = courseService.save(courseToSave, professor);
        assertEquals(2, inMemoryCourseRepository.size());
        assertEquals(courseToSave.getTitle(), returnedCourse.getTitle());
    }

    @Test
    public void deleteTest() {
        student = setUpStudent();
        assertFalse(courseService.delete(1L, student));

        assertTrue(courseService.delete(courseToSave.getId(), professor));
        assertEquals(1, inMemoryCourseRepository.size());
    }

}
