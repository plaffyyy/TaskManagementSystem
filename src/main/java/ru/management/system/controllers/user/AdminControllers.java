package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.management.system.dto.task.*;
import ru.management.system.entities.user.User;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminControllers {

    private final UserService userService;
    private final TaskService taskService;

    @Operation(summary = "Создание задачи для админа")
    @PostMapping("/create")
    public void createTask(@RequestBody @Valid CreateTaskRequest request) {
        taskService.saveTask(request);
    }
    @Operation(summary = "Редактирование описания для задачи")
    @PatchMapping("/update-description")
    public void updateDescription(@RequestBody @Valid UpdateRequest request) {

        taskService.updateDescriptionForTask(request.name(), request.newDescription());

    }

    @Operation(summary = "Назначение исполнителей задачи по email")
    @PatchMapping("/update-assignees")
    public void updateAssignees(@RequestBody @Valid UpdateRequest request) {
        User user = userService.getByEmail(request.email());
        var task = taskService.updateAssigneesForTask(request.name(), user);
        userService.updateTaskAssigned(user, task);
    }

    @Operation(summary = "Поменять статус задачи")
    @PatchMapping("/update-status")
    public void updateStatus(@RequestBody @Valid UpdateRequest request) {

        taskService.updateStatusForTask(request.name(), request.newStatus());
    }

    @Operation(summary = "Поменять статус задачи")
    @PatchMapping("/update-priority")
    public void updatePriority(@RequestBody @Valid UpdateRequest request) {

        taskService.updatePriorityForTask(request.name(), request.newPriority());

    }
    @Operation(summary = "Добавить комментарий к задаче")
    @PatchMapping("/add-comment")
    public void addComment(@RequestBody @Valid UpdateRequest request) {

        taskService.addComment(request.name(), request.commentText());

    }

    @Operation(summary = "Удалить задачу")
    @PatchMapping("/delete-task")
    public void deleteTask(@RequestBody @Valid DeleteRequest request) {
        taskService.deleteTask(request.name());
    }

    @Operation(summary = "Получить задачи конкретного исполнителя", description = "В поле sortBy вы можете передать только поля объекта Task")
    @PostMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@RequestBody GetTasksRequest request) {
        User user = userService.getByEmail(request.email());
        List<TaskDto> tasks = taskService.getPerPage(
            user, request.indexOfPage(), request.itemsPerPage(),request.sortBy()
        );
        return ResponseEntity.ok(tasks);

    }



}
