package ua.opnu.labwork2.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTask_Id(Long taskId);
}