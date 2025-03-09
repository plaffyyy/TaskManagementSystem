package ru.management.system.controllers.security;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.management.system.dto.security.JwtAuthenticationResponse;
import ru.management.system.dto.security.SignInRequest;
import ru.management.system.dto.security.SignUpRequest;
import ru.management.system.services.security.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public final class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователей")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

}
