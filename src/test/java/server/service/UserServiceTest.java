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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static server.service.Utils.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private List<User> inMemoryUserRepository = new ArrayList<>();

    private User professor = new User();

    private User student = new User();

    private User studentToSave = new User();

    private User userNotSupported = new User();

    @Before
    public void init() {
        userService = new UserService(userRepository);

        userNotSupported.setEmail(EMAIL_NOT_SUPPORTED);
        student = setUpStudent();
        professor = setUpProfessor();

        inMemoryUserRepository.add(professor);
        inMemoryUserRepository.add(student);

        when(userRepository.findAll()).thenReturn(inMemoryUserRepository);
        when(userRepository.findByEmail(PROFESSOR_EMAIL)).thenReturn(Optional.of(professor));
        when(userRepository.findByEmail(STUDENT_EMAIL)).thenReturn(Optional.of(student));
        when(userRepository.findAllByRole(Role.STUDENT)).thenReturn(Optional.of(Collections.singletonList(student)));
        when(userRepository.findAllByRole(Role.PROFESSOR)).thenReturn(Optional.of(Collections.singletonList(professor)));

        studentToSave = setUpUserToSave();
        when(userRepository.save(studentToSave)).thenAnswer(invocation -> {
            inMemoryUserRepository.add(studentToSave);
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
        assertEquals(professor, userService.getByEmail(PROFESSOR_EMAIL));
        assertEquals(student, userService.getByEmail(STUDENT_EMAIL));
        assertNull(userService.getByEmail(""));
        assertNull(userService.getByEmail(null));
    }

    @Test
    public void registerTest() {
        assertFalse(userService.register(null));
        assertFalse(userService.register(userNotSupported));

        assertEquals(2, inMemoryUserRepository.size());
        assertTrue(userService.register(studentToSave));
        assertEquals(3, inMemoryUserRepository.size());
        assertEquals(studentToSave, inMemoryUserRepository.get(2));

        assertFalse(userService.register(professor));
        assertEquals(3, inMemoryUserRepository.size());

        assertFalse(userService.register(null));
    }

    @Test
    public void getAllByRoleTest() {
        List<User> students = userService.getAllBy(Role.STUDENT);
        assertEquals(1, students.size());
        for (User student : students) {
            assertEquals(Role.STUDENT, student.getRole());
        }

        List<User> professors = userService.getAllBy(Role.PROFESSOR);
        assertEquals(1, professors.size());
        for (User professor : professors) {
            assertEquals(Role.PROFESSOR, professor.getRole());
        }
    }

    @Test
    public void isNotValidTest() {
        assertTrue(userService.isNotValid(EMAIL_NOT_SUPPORTED, ""));
        assertTrue(userService.isNotValid(STUDENT_TO_SAVE_EMAIL, ""));
        assertFalse(userService.isNotValid(STUDENT_EMAIL, PASSWORD));
    }
}
