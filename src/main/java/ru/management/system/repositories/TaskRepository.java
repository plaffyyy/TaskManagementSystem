package ru.management.system.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.management.system.entities.task.Task;
import ru.management.system.entities.user.User;

import java.util.List;
import java.util.Set;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Task getTaskByName(String name);
    List<Task> findTasksByAssignees(Set<User> assignees, PageRequest pageable);
}

