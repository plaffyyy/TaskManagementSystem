package ru.management.system.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.management.system.dto.task.AssignUserRequest;
import ru.management.system.dto.task.CreateTaskRequest;
import ru.management.system.dto.task.UpdateDescriptionRequest;
import ru.management.system.entities.task.Task;
import ru.management.system.entities.user.User;
import ru.management.system.exceptions.TaskIsNotUniqueException;
import ru.management.system.exceptions.TaskNotFoundException;
import ru.management.system.repositories.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * проверка что у новой задачи уникальное имя
     * @param request запрос на создание задачи
     * @return boolean уникальна или нет
     */
    private boolean isUniqueTaskName(CreateTaskRequest request) {
        List<Task> tasks = taskRepository.findAll();
        for (Task task: tasks) {
            if (task.getName().equals(request.name())) return false;
        }
        return true;
    }
    /**
     *
     * @param request dto для создания задачи
     * по дефолту создается задача, которая никому не назначена
     */
    public void saveTask(CreateTaskRequest request) {
        if (!isUniqueTaskName(request)) throw new TaskIsNotUniqueException("Данная задача уже существует");
        Task task = new Task(
                request.name(),
                request.description()
        );
        taskRepository.save(task);

    }

    /**
     *
     * @param taskName название задачи
     * @param newDescription новое описание задачи
     */
    public void updateDescriptionForTasks(String taskName, String newDescription) {
        if (taskName == null || taskRepository.getTaskByName(taskName) == null) throw new TaskNotFoundException("Нет задачи с таким именем");
        Task task = taskRepository.getTaskByName(taskName);

        task.setDescription(newDescription);
        taskRepository.save(task);

    }

    /**
     *
     * @param taskName имя задачи для изменения
     * @param user для какого пользователя добавляем задачу
     * @return задачу которую добавили
     */
    public Task updateAssigneesForTask(String taskName, User user) {
        if (taskName == null || taskRepository.getTaskByName(taskName) == null) throw new TaskNotFoundException("Нет задачи с таким именем");
        Task task = taskRepository.getTaskByName(taskName);

        task.getAssignees().add(user);
        taskRepository.save(task);
        return task;
    }
}
