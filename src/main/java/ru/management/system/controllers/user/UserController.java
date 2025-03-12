package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.management.system.services.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String user() {
        return "Hello, user!";
    }


    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN")
    public void getAdmin() {
        userService.getAdmin();
    }

}
