package ua.opnu.labwork2.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.opnu.labwork2.exception.ResourceNotFoundException;

import ua.opnu.labwork2.project.model.Project;
import ua.opnu.labwork2.project.repository.ProjectRepository;

import ua.opnu.labwork2.task.dto.TaskCreateRequest;
import ua.opnu.labwork2.task.dto.TaskResponse;
import ua.opnu.labwork2.task.dto.TaskUpdateRequest;
import ua.opnu.labwork2.task.model.Task;
import ua.opnu.labwork2.task.repository.TaskRepository;

import ua.opnu.labwork2.tag.model.Tag;
import ua.opnu.labwork2.tag.repository.TagRepository;

import ua.opnu.labwork2.user.dto.UserResponse;
import ua.opnu.labwork2.tag.dto.TagResponse;
import ua.opnu.labwork2.comment.dto.CommentResponse;

import java.util.List;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @Transactional
    public TaskResponse create(TaskCreateRequest request) {

        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + request.projectId())
                );

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setProject(project);

        return mapToResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public TaskResponse findById(Long id) {
        return taskRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + id)
                );
    }

    @Transactional
    public TaskResponse update(Long id, TaskUpdateRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + id)
                );

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());

        return mapToResponse(taskRepository.save(task));
    }

    @Transactional
    public void delete(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }

        taskRepository.deleteById(id);
    }

    @Transactional
    public TaskResponse addTag(Long taskId, Long tagId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + taskId)
                );

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tag not found with id: " + tagId)
                );

        task.getTags().add(tag);

        return mapToResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse removeTag(Long taskId, Long tagId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + taskId)
                );

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tag not found with id: " + tagId)
                );

        task.getTags().remove(tag);

        return mapToResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getTaskUsers(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + taskId)
                );

        return task.getUsers() == null ? Collections.emptyList() :
                task.getUsers()
                        .stream()
                        .map(user -> new UserResponse(
                                user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.getRole()
                        ))
                        .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findTasksByProjectId(Long projectId) {

        projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + projectId)
                );

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),

                task.getUsers() == null ? Collections.emptyList() :
                        task.getUsers().stream()
                                .map(user -> new UserResponse(
                                        user.getId(),
                                        user.getFirstName(),
                                        user.getLastName(),
                                        user.getEmail(),
                                        user.getRole()
                                ))
                                .toList(),

                task.getTags() == null ? Collections.emptyList() :
                        task.getTags().stream()
                                .map(tag -> new TagResponse(
                                        tag.getId(),
                                        tag.getName(),
                                        tag.getDescription()
                                ))
                                .toList(),

                task.getComments() == null ? Collections.emptyList() :
                        task.getComments().stream()
                                .map(comment -> new CommentResponse(
                                        comment.getId(),
                                        comment.getText(),
                                        comment.getCreatedDate()
                                ))
                                .toList()
        );
    }
}