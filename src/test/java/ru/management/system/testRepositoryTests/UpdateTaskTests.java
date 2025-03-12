package ru.management.system.testRepositoryTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.management.system.entities.comment.Comment;
import ru.management.system.entities.task.Priority;
import ru.management.system.entities.task.Task;
import ru.management.system.exceptions.UndefinedPriorityException;
import ru.management.system.exceptions.UndefinedStatusException;
import ru.management.system.repositories.CommentRepository;
import ru.management.system.repositories.TaskRepository;
import ru.management.system.services.TaskService;
import ru.management.system.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateTaskTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void updatePriorityForTask() {
        String taskName = "Task 1";
        String newPriority = "HIGH";
        Task task = new Task("Task 1", "Some Description");

        when(taskRepository.getTaskByName(taskName)).thenReturn(task);
        when(userService.isAdmin()).thenReturn(true);

        taskService.updatePriorityForTask(taskName, newPriority);

        assertEquals(Priority.HIGH, task.getPriority());
        verify(taskRepository, times(1)).save(task); // проверяем, что задача сохраняется в репозитории
    }

    @Test
    void throwUndefinedPriorityExceptionWhenStatusIsInvalid() {
        String taskName = "Task 1";
        String newPriority = "HIGasdH";
        Task task = new Task("Task 1", "Some Description");

        when(taskRepository.getTaskByName(taskName)).thenReturn(task);
        when(userService.isAdmin()).thenReturn(true);

        assertThrows(UndefinedPriorityException.class, () -> {
            taskService.updatePriorityForTask(taskName, newPriority);
        });
    }
    @Test
    void addCommentToTask() {

        String taskName = "Task 1";
        String commentText = "This is a comment";
        Task task = new Task("Task 1", "Some Description");

        when(taskRepository.getTaskByName(taskName)).thenReturn(task);
        when(userService.isAdmin()).thenReturn(true);

        taskService.addComment(taskName, commentText);

        assertEquals(1, task.getComments().size()); // проверяем, что комментарий был добавлен
        assertEquals(commentText, task.getComments().get(0).getText()); // проверяем, что текст комментария совпадает
        verify(commentRepository, times(1)).save(any(Comment.class)); // проверяем, что комментарий сохранен
    }

}
