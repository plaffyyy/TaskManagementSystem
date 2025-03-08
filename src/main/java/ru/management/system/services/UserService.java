package ru.management.system.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.management.system.exceptions.EmailNotUniqueException;
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
    public User save(User user) {
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



}
