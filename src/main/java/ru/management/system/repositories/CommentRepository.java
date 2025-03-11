package ru.management.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.management.system.entities.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
