package ru.management.system.testRepositoryTests;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.management.system.dto.task.CreateTaskRequest;
import ru.management.system.entities.task.Status;
import ru.management.system.entities.task.Task;
import ru.management.system.exceptions.UndefinedStatusException;
import ru.management.system.exceptions.UserIsNotAdminException;
import ru.management.system.repositories.TaskRepository;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveUpdateTaskTests {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void saveTaskOnlyOne() {
        CreateTaskRequest request = new CreateTaskRequest("Task 1", "Description");

        when(taskRepository.findAll()).thenReturn(List.of());
        when(userService.isAdmin()).thenReturn(true);

        taskService.saveTask(request);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void userIsNotAnAdmin() {
        CreateTaskRequest request = new CreateTaskRequest("Task 1", "Description");

        when(userService.isAdmin()).thenReturn(false);

        assertThrows(UserIsNotAdminException.class, () -> taskService.saveTask(request));
    }

    @Test
    void updateStatusForTask() {
        // Arrange
        String taskName = "Task 1";
        String newStatus = "EXECUTED";
        Task task = new Task("Task 1", "Some Description");

        when(taskRepository.getTaskByName(taskName)).thenReturn(task);
        when(userService.isAdmin()).thenReturn(true);

        taskService.updateStatusForTask(taskName, newStatus);

        assertEquals(Status.EXECUTED, task.getStatus());
        verify(taskRepository, times(1)).save(task); // проверяем, что задача сохраняется в репозитории
    }

    @Test
    void throwUndefinedStatusExceptionWhenStatusIsInvalid() {
        // Arrange
        String taskName = "Task 1";
        String invalidStatus = "INVALID_STATUS";
        Task task = new Task("Task 1", "Some Description");

        when(taskRepository.getTaskByName(taskName)).thenReturn(task);
        when(userService.isAdmin()).thenReturn(true);

        assertThrows(UndefinedStatusException.class, () -> {
            taskService.updateStatusForTask(taskName, invalidStatus);
        });
    }

}