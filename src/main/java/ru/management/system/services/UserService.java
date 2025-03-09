package ru.management.system.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.management.system.exceptions.EmailNotUniqueException;
import ru.management.system.exceptions.UserNotFoundException;
import ru.management.system.pojo.user.Role;
import ru.management.system.pojo.user.User;
import ru.management.system.repositories.UserRepository;

@AllArgsConstructor
@Service
public final class UserService {

    @Autowired
    private final UserRepository userRepository;

    /**
     *
     * @param user пользователь в регистрации
     * @return этот же пользователь
     */
    private User save(User user) {
        return userRepository.save(user);
    }

    /**
     *
     * @param user пользователь в регистрации
     * @return зарегистрированный пользователь
     * @throws EmailNotUniqueException ошибка, если email уже был использован
     */
    public User create(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailNotUniqueException("Пользователь с таким email уже существует!");
        }
        return save(user);

    }

    /**
     *
     * @param email email пользователя
     * @return user
     * @throws UserNotFoundException если пользователя с таким email не существует
     */
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с данных email не найден"));
    }

    /**
     * Получение пользователя по email
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    /**
     * Получение текущего пользователя из контекста Spring Security
     *
     * @return
     */
    public User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }

    /**
     * Выдача прав администратора текущему пользователю
     */
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ADMIN);
        save(user);
    }
}
