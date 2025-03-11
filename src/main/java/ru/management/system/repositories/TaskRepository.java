package ru.management.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.management.system.entities.task.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
