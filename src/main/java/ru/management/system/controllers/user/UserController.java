package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.management.system.dto.user.UpdateForUserRequest;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @PostMapping("/update-status")
    @Operation(summary = "Обновить статус если текущий пользователь имеет эту задачу")
    public void updateStatus(@RequestBody @Valid UpdateForUserRequest request) {

        taskService.updateStatusForTask(request.name(), request.newStatus());

    }
    @PostMapping("/add-comment")
    @Operation(summary = "Добавить комментарий если текущий пользователь имеет эту задачу")
    public void addComment(@RequestBody @Valid UpdateForUserRequest request) {

        taskService.updateStatusForTask(request.name(), request.commentText());

    }

}
