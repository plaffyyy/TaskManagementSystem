package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.management.system.dto.task.AssignUserRequest;
import ru.management.system.dto.task.CreateTaskRequest;
import ru.management.system.dto.task.UpdateDescriptionRequest;
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
        if (userService.isAdmin()) {
            taskService.saveTask(request);
        }

    }
    @Operation(summary = "Редактирование описания для задачи")
    @PatchMapping("/update-description")
    public void updateDescription(@RequestBody @Valid UpdateDescriptionRequest request) {

        if (userService.isAdmin()) {
            taskService.updateDescriptionForTasks(request);
        }

    }

    @Operation(summary = "Назначение исполнителей задачи по email")
    @PatchMapping("/update-assignees")
    public void updateAssignees(@RequestBody @Valid AssignUserRequest request) {

        if (userService.isAdmin()) {
            User user = userService.getByEmail(request.email());
            var task = taskService.updateAssigneesForTask(request, user);
            userService.updateTaskAssigned(user, task);
        }
    }


}
