package server.service;

import com.server.model.Lecture;
import com.server.repository.CourseRepository;
import com.server.repository.LectureRepository;
import com.server.service.LectureService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceTest {

    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private CourseRepository courseRepository;

    private List<Lecture> inMemoryRepository = new ArrayList<>();

    @Before
    public void init() {
        lectureService = new LectureService(lectureRepository, courseRepository);
    }
}
