package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.management.system.dto.user.CreateTaskRequest;
import ru.management.system.entities.user.User;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminControllers {

    private final UserService userService;
    private final TaskService taskService;

    @Operation(summary = "Создание задачи для админа")
    @PostMapping("/create")
    public void createTask(@RequestBody @Valid CreateTaskRequest request) {

        User user = userService.getCurrentUser();
        var task = taskService.saveTask(request, user);
        userService.createTask(user, task);

    }

}
