package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.management.system.dto.task.CreateTaskRequest;
import ru.management.system.dto.task.DeleteRequest;
import ru.management.system.dto.task.UpdateRequest;
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
    public void updateDescription(@RequestBody @Valid UpdateRequest request) {

        if (userService.isAdmin()) {
            taskService.updateDescriptionForTask(request.name(), request.newDescription());
        }

    }

    @Operation(summary = "Назначение исполнителей задачи по email")
    @PatchMapping("/update-assignees")
    public void updateAssignees(@RequestBody @Valid UpdateRequest request) {

        if (userService.isAdmin()) {
            User user = userService.getByEmail(request.email());
            var task = taskService.updateAssigneesForTask(request.name(), user);
            userService.updateTaskAssigned(user, task);
        }
    }

    @Operation(summary = "Поменять статус задачи")
    @PatchMapping("/update-status")
    public void updateStatus(@RequestBody @Valid UpdateRequest request) {

        if (userService.isAdmin()) {
            taskService.updateStatusForTask(request.name(), request.newStatus());
        }
    }

    @Operation(summary = "Поменять статус задачи")
    @PatchMapping("/update-priority")
    public void updatePriority(@RequestBody @Valid UpdateRequest request) {

        if (userService.isAdmin()) {
            taskService.updatePriorityForTask(request.name(), request.newPriority());
        }
    }
    @Operation(summary = "Добавить комментарий к задаче")
    @PatchMapping("/add-comment")
    public void addComment(@RequestBody @Valid UpdateRequest request) {

        if (userService.isAdmin()) {
            taskService.addComment(request.name(), request.commentText());
        }

    }

    @Operation(summary = "Удалить задачу")
    @PatchMapping("/delete-task")
    public void deleteTask(@RequestBody @Valid DeleteRequest request) {

        if (userService.isAdmin()) {
            taskService.deleteTask(request.name());
        }

    }



}
