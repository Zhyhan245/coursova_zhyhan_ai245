package ua.opnu.labwork2.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.comment.dto.CommentCreateRequest;
import ua.opnu.labwork2.comment.dto.CommentResponse;
import ua.opnu.labwork2.comment.dto.CommentUpdateRequest;
import ua.opnu.labwork2.comment.model.Comment;
import ua.opnu.labwork2.comment.repository.CommentRepository;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.task.model.Task;
import ua.opnu.labwork2.task.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public CommentResponse create(CommentCreateRequest request) {

        Task task = taskRepository.findById(request.taskId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + request.taskId())
                );

        Comment comment = new Comment();
        comment.setText(request.text());
        comment.setTask(task);

        Comment saved = commentRepository.save(comment);

        return mapToResponse(saved);
    }

    public List<CommentResponse> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CommentResponse findById(Long id) {
        return commentRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found with id: " + id)
                );
    }

    public List<CommentResponse> findCommentsByTaskId(Long taskId) {

        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }

        return commentRepository.findByTask_Id(taskId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public CommentResponse update(Long id, CommentUpdateRequest request) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found with id: " + id)
                );

        comment.setText(request.text());

        Comment updated = commentRepository.save(comment);

        return mapToResponse(updated);
    }

    @Transactional
    public void delete(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found with id: " + id)
                );

        commentRepository.delete(comment);
    }

    private CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getCreatedDate()
        );
    }
}