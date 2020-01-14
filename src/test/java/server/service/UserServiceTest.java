package server.service;

import com.server.model.User;
import com.server.model.enums.Role;
import com.server.repository.UserRepository;
import com.server.service.UserService;
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
public class UserServiceTest {
    private static final String EMAIL_PROFESSOR = "profesor@cs.ubbcluj.ro";

    private static final String EMAIL_STUDENT = "student@scs.ubbcluj.ro";

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private List<User> inMemoryRepository = new ArrayList<>();

    private User professor = new User();
    private User student = new User();
    private User studentToSave = new User();


    @Before
    public void init() {
        userService = new UserService(userRepository);

        inMemoryRepository = mockRepository();
        when(userRepository.findAll()).thenReturn(inMemoryRepository);
        when(userRepository.findByEmail(EMAIL_PROFESSOR)).thenReturn(Optional.of(professor));
        when(userRepository.findByEmail(EMAIL_STUDENT)).thenReturn(Optional.of(student));

        setUpUserToSave();
        when(userRepository.save(studentToSave)).thenAnswer(invocation -> {
            inMemoryRepository.add(studentToSave);
            return studentToSave;
        });
    }

    @Test
    public void getAllTest() {
        List<User> users = userService.getAll();
        assertEquals(2, users.size());
        assertEquals(professor, users.get(0));
        assertEquals(student, users.get(1));
    }

    @Test
    public void getByEmailTest() {
        assertEquals(professor, userService.getByEmail(EMAIL_PROFESSOR));
        assertEquals(student, userService.getByEmail(EMAIL_STUDENT));
        assertNull(userService.getByEmail(""));
        assertNull(userService.getByEmail(null));
    }

    @Test
    public void registerTest() {
        assertEquals(2, inMemoryRepository.size());
        assertTrue(userService.register(studentToSave));
        assertEquals(3, inMemoryRepository.size());
        assertEquals(studentToSave, inMemoryRepository.get(2));

        assertFalse(userService.register(professor));
        assertEquals(3, inMemoryRepository.size());

        assertFalse(userService.register(null));

    }

    private void setUpUserToSave() {
        studentToSave.setEmail("studentToSave@scs.ubbcluj.ro");
    }

    private List<User> mockRepository() {
        professor.setRole(Role.PROFESSOR);
        professor.setEmail(EMAIL_PROFESSOR);
        professor.setName("Ioan");
        professor.setSurname("Pop");

        student.setRole(Role.STUDENT);
        student.setEmail(EMAIL_STUDENT);
        student.setName("Gigel");
        student.setSurname("Frasinariu");

        inMemoryRepository.add(professor);
        inMemoryRepository.add(student);

        return inMemoryRepository;
    }
}
