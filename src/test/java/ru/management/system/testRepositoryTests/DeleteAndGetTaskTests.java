package ru.management.system.testRepositoryTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.management.system.dto.task.TaskDto;
import ru.management.system.entities.comment.Comment;
import ru.management.system.entities.task.Priority;
import ru.management.system.entities.task.Task;
import ru.management.system.entities.user.Role;
import ru.management.system.entities.user.User;
import ru.management.system.exceptions.TaskNotFoundException;
import ru.management.system.exceptions.UndefinedPriorityException;
import ru.management.system.repositories.CommentRepository;
import ru.management.system.repositories.TaskRepository;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAndGetTaskTests {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void deleteTaskSuccessfully() {
        Task task = new Task("Test Task", "Description");
        User user1 = new User(1L, "password", "test1@example.com", Role.ROLE_USER, new HashSet<>());
        User user2 = new User(2L, "password", "test2@example.com", Role.ROLE_USER, new HashSet<>());
        task.getAssignees().add(user1);
        task.getAssignees().add(user2);
        user1.getAssignedTasks().add(task);
        user2.getAssignedTasks().add(task);

        when(taskRepository.getTaskByName("Test Task")).thenReturn(task);
        when(userService.isAdmin()).thenReturn(true);

        taskService.deleteTask("Test Task");


        assertFalse(user1.getAssignedTasks().contains(task));
        assertFalse(user2.getAssignedTasks().contains(task));
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTask() {

        when(taskRepository.getTaskByName("NonExistingTask")).thenReturn(null);
        when(userService.isAdmin()).thenReturn(true);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask("NonExistingTask"));
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void getTasksPerPageSuccessfully() {
        User user = new User(1L, "password", "test1@example.com", Role.ROLE_USER, new HashSet<>());
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        List<Task> taskList = List.of(task1);

        when(taskRepository.findTasksByAssignees(
                eq(Set.of(user)), any(PageRequest.class))
        ).thenReturn(taskList);

        List<TaskDto> tasks = taskService.getPerPage(user, 0, 1, "name");

        assertEquals(1, tasks.size());
        assertEquals("Task 1", tasks.get(0).name());
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.get(1).name());
    }

}
