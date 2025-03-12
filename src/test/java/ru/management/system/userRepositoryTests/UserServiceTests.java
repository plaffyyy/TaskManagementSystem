package ru.management.system.userRepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.management.system.entities.task.Task;
import ru.management.system.entities.user.Role;
import ru.management.system.entities.user.User;
import ru.management.system.exceptions.EmailNotUniqueException;
import ru.management.system.exceptions.UserNotFoundException;
import ru.management.system.repositories.UserRepository;
import ru.management.system.services.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createUserSuccessfully() {

        User user = new User(1L, "password", "test@example.com", Role.ROLE_USER, null);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);


        User createdUser = userService.create(user);

        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void throwExceptionWhenEmailIsNotUnique() {

        User user = new User(1L, "password", "test@example.com", Role.ROLE_USER, null);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(EmailNotUniqueException.class, () -> userService.create(user));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserByEmail() {
        User user = new User(1L, "password", "test@example.com", Role.ROLE_USER, null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User foundUser = userService.getByEmail(user.getEmail());

        assertEquals(user, foundUser);
    }

    @Test
    void throwExceptionWhenUserNotFoundByEmail() {

        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByEmail("notfound@example.com"));
    }

    @Test
    void shouldReturnCurrentUser() {

        User user = new User(1L, "password", "test@example.com", Role.ROLE_USER, null);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User currentUser = userService.getCurrentUser();

        assertEquals(user, currentUser);
    }

    @Test
    void adminRoleToCurrentUser() {

        User user = new User(1L, "password", "test@example.com", Role.ROLE_USER, null);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);


        userService.getAdmin();

        assertEquals(Role.ROLE_ADMIN, user.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateTaskAssignedToUser() {
        User user = new User(1L, "password", "test@example.com", Role.ROLE_USER, new HashSet<>());
        Task task = new Task("New Task", "Description");
        when(userRepository.save(user)).thenReturn(user);

        userService.updateTaskAssigned(user, task);

        assertTrue(user.getAssignedTasks().contains(task));
        verify(userRepository, times(1)).save(user);
    }
}
