package ru.management.system.entities.comment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.management.system.entities.task.Task;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    public Comment(String text, Task task) {
        this.text = text;
        this.task = task;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
