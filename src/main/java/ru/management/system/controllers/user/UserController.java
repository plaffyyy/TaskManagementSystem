package ru.management.system.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.management.system.dto.task.UpdateStatusRequest;
import ru.management.system.entities.user.User;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = )
    public void updateStatus(@RequestBody @Valid UpdateStatusRequest request) {

        User user = userService.getCurrentUser();
        taskService.updateStatusForTask(request.name(), request.newStatus());


    }

}
