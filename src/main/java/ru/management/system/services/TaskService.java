package ru.management.system.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.management.system.dto.user.CreateTaskRequest;
import ru.management.system.entities.task.Task;
import ru.management.system.entities.user.User;
import ru.management.system.repositories.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     *
     * @param request dto для создания задачи
     * @param user пользователь
     * @return задача
     */
    public Task saveTask(CreateTaskRequest request, User user) {
        Task task = new Task(
                request.name(),
                request.description()
        );
        task.getAssignees().add(user);
        taskRepository.save(task);
        return task;
    }

}
