package ru.management.system.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.management.system.dto.task.AssignUserRequest;
import ru.management.system.dto.task.CreateTaskRequest;
import ru.management.system.dto.task.UpdateDescriptionRequest;
import ru.management.system.entities.task.Task;
import ru.management.system.entities.user.User;
import ru.management.system.exceptions.TaskIsNotUniqueException;
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
     * @param request json с новым описанием и названием задачи
     * @return Task возвращаем задачу с отредактированным описанием
     * все задачи имеют уникальные имена
     */
    public void updateDescriptionForTasks(UpdateDescriptionRequest request) {

        Task task = taskRepository.getTaskByName(request.name());

        task.setDescription(request.newDescription());
        taskRepository.save(task);

    }

    public Task updateAssigneesForTask(AssignUserRequest request, User user) {

        Task task = taskRepository.getTaskByName(request.name());
        task.getAssignees().add(user);
        taskRepository.save(task);
        return task;
    }
}
