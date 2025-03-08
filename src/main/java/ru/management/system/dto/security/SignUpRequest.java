package ru.management.system.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на регистрацию")
public record SignUpRequest(
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен быть от 5 до 255 символов")
    @Email(message = "Email должен быть в формате example@example.example")
    String email,
    @Size(max = 255, message = "Пароль не должен содержать более 255 символов")
    String password

) {}
