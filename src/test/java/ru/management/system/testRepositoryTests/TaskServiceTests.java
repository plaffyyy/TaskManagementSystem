package ru.management.system.testRepositoryTests;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.management.system.dto.task.CreateTaskRequest;
import ru.management.system.entities.task.Task;
import ru.management.system.repositories.TaskRepository;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {
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

}
