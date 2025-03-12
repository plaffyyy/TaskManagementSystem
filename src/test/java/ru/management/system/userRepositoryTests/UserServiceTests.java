package ru.management.system.userRepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.management.system.entities.user.Role;
import ru.management.system.entities.user.User;
import ru.management.system.exceptions.EmailNotUniqueException;
import ru.management.system.repositories.UserRepository;
import ru.management.system.services.UserService;

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

}
