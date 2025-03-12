package ru.management.system.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.management.system.dto.task.CreateTaskRequest;
import ru.management.system.dto.task.TaskDto;
import ru.management.system.entities.comment.Comment;
import ru.management.system.entities.task.Priority;
import ru.management.system.entities.task.Status;
import ru.management.system.entities.task.Task;
import ru.management.system.entities.user.User;
import ru.management.system.exceptions.*;
import ru.management.system.repositories.CommentRepository;
import ru.management.system.repositories.TaskRepository;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    /**
     * проверка есть ли права у пользователя
     * @throws UserIsNotAdminException если нет прав
     */
    private void isAdmin() {
        if (!userService.isAdmin()) throw new UserIsNotAdminException("У пользователя нет прав администратора");
    }

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
        isAdmin();
        if (!isUniqueTaskName(request)) throw new TaskIsNotUniqueException("Данная задача уже существует");
        Task task = new Task(
                request.name(),
                request.description()
        );
        taskRepository.save(task);

    }

    /**
     * проверяем есть ли задача с таким именем и получаем ее
     * и есть ли права администратора для этого
     * @param taskName имя задачи
     * @return task задача
     * @throws UserIsNotAdminException если нет прав админа
     * @throws TaskNotFoundException если нет задачи с таким именем
     */
    private Task getTaskByName(String taskName) {
        isAdmin();
        if (taskName == null || taskRepository.getTaskByName(taskName) == null) throw new TaskNotFoundException("Нет задачи с таким именем");
        return taskRepository.getTaskByName(taskName);
    }


    /**
     * проверка, что если пользователь не админ
     * то он должен иметь эту задачу для ее изменения
     * @param user пользователь
     * @param taskName название задачи
     * @throws UserNotHasThisTask если нет этой задачи у пользователя
     * @throws TaskNotFoundException если не найдена задача
     */
    private Task hasThisTask(User user, String taskName) {
        Task task = taskRepository.getTaskByName(taskName);
        if (taskName == null || taskRepository.getTaskByName(taskName) == null) throw new TaskNotFoundException("Нет задачи с таким именем");
        if (!user.getAssignedTasks().contains(task)) throw new UserNotHasThisTask("У данного пользователя нет этой задачи");
        return task;
    }

    /**
     *
     * @param taskName название задачи
     * @param newDescription новое описание задачи
     */
    public void updateDescriptionForTask(String taskName, String newDescription) {
        Task task = getTaskByName(taskName);

        task.setDescription(newDescription);
        taskRepository.save(task);

    }

    /**
     * обновляем статус по строке
     * @param taskName название задачи
     * @param newStatus новый статус задачи
     * @throws UndefinedStatusException если нет такого статуса в enum Status
     */
    public void updateStatusForTask(String taskName, String newStatus) {
        Task task = getTaskByName(taskName);
        if (task == null) {
            task = hasThisTask(userService.getCurrentUser(),taskName);
        }

        task.setStatus(Status.statusFromString(newStatus));
        taskRepository.save(task);

    }

    /**
     * обновляем приоритет по строке
     * @param taskName название задачи
     * @param newPriority новый приоритет задачи
     * @throws UndefinedPriorityException если нет такого приоритета в enum Priority
     */
    public void updatePriorityForTask(String taskName, String newPriority) {
        Task task = getTaskByName(taskName);

        task.setPriority(Priority.priorityFromString(newPriority));
        taskRepository.save(task);

    }


    /**
     *
     * @param taskName имя задачи для изменения
     * @param user для какого пользователя добавляем задачу
     * @return задачу, которую добавили
     */
    public Task updateAssigneesForTask(String taskName, User user) {
        Task task = getTaskByName(taskName);

        task.getAssignees().add(user);
        taskRepository.save(task);
        return task;
    }

    /**
     * добавляем новый комментарий к задаче
     * @param taskName имя задачи, чтобы ее найти
     * @param commentText текст комментария
     */
    public void addComment(String taskName, String commentText) {
        Task task = getTaskByName(taskName);
        if (task == null) {
            task = hasThisTask(userService.getCurrentUser(),taskName);
        }

        Comment comment = new Comment(commentText, task);
        commentRepository.save(comment);
        task.getComments().add(comment);
        taskRepository.save(task);
    }

    /**
     * удаляем задачу из списка задач, и у всех пользователей,
     * которые выполняют эту задачу
     * @param taskName имя задачи
     */
    public void deleteTask(String taskName) {
        Task task = getTaskByName(taskName);
        Set<User> users = task.getAssignees();
        for (User user: users) {
            user.getAssignedTasks().remove(task);
        }

        taskRepository.delete(task);
    }

    private boolean checkSortType(String sortBy) {
        Method[] methods = Task.class.getDeclaredMethods();
        for (Method method: methods) {
            if (method.getName().equals(sortBy)) {
                return false;
            }
        }
        return true;
    }

    /**
     * получение всех задач пользователя указывая
     * количество задач на страницу и индекс страницы
     * с сортировкой по полю из класса Task
     * @param user пользователь, чьи задачи получаем
     * @param indexPage индекс страницы начиная с 0
     * @param itemsPerPage количество элементов на страницу
     * @param sortBy аргумент сортировки из класса Task
     * @return задачи по шаблону TaskDto
     */
    public List<TaskDto> getPerPage(User user, int indexPage, int itemsPerPage, String sortBy) {
        if (!checkSortType(sortBy)) throw new IncorrectSortTypeException("Введите сортировку по методу из класса Task");
        if (indexPage < 0) {
            indexPage = 0;
        }
        if (itemsPerPage < 0) {
            itemsPerPage = 0;
        }
        PageRequest pageable = PageRequest.of(
                indexPage, itemsPerPage,
                Sort.by(sortBy));

        List<Task> tasks = taskRepository.findTasksByAssignees(Set.of(user), pageable);
        return tasks.stream()
                .map(TaskDto::fromEntity)
                .toList();
    }

}
