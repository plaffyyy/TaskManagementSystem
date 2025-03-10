package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.management.system.services.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String user() {
        return "Hello, world!";
    }

//    @GetMapping("/admin")
//    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
//    @PreAuthorize("hasRole('ROLE_ADMIN')") //spring security annotation
//    public String admin() {
//        return "Hello, admin!";
//    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN")
    public void getAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User roles: " + authentication.getAuthorities());
        userService.getAdmin();
    }

}
