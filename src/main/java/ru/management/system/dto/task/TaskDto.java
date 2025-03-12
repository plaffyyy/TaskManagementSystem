package ru.management.system.dto.task;

import ru.management.system.entities.comment.Comment;
import ru.management.system.entities.task.Task;

import java.util.List;

public record TaskDto(
        Long id, String name,
        String description, String status,
        String priority, List<Comment> comments
) {
    public static TaskDto fromEntity(Task task) {
        return new TaskDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus().toString(),
                task.getPriority().toString(),
                task.getComments()
        );
    }
}
