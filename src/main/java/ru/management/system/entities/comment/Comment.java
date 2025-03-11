package ru.management.system.entities.comment;

import jakarta.persistence.*;
import lombok.Data;
import ru.management.system.entities.task.Task;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
