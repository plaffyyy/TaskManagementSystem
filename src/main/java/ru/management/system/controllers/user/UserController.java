package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.management.system.dto.user.UpdateForUserRequest;
import ru.management.system.services.TaskService;

@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final TaskService taskService;

    @PostMapping("/update-status")
    @Operation(summary = "Обновить статус если текущий пользователь имеет эту задачу")
    public void updateStatus(@Valid @RequestBody UpdateForUserRequest request) {
        log.info("Received request: {}", request);
        log.info(request.name());
        taskService.updateStatusForTask(request.name(), request.newStatus());

    }
    @PostMapping("/add-comment")
    @Operation(summary = "Добавить комментарий если текущий пользователь имеет эту задачу")
    public void addComment(@RequestBody @Valid UpdateForUserRequest request) {

        taskService.addComment(request.name(), request.commentText());

    }

}
