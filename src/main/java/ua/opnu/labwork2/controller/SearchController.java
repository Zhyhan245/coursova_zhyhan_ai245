package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.opnu.labwork2.tag.dto.TagResponse;
import ua.opnu.labwork2.task.dto.TaskResponse;
import ua.opnu.labwork2.task.model.Task;
import ua.opnu.labwork2.task.model.TaskPriority;
import ua.opnu.labwork2.task.model.TaskStatus;
import ua.opnu.labwork2.task.repository.TaskRepository;
import ua.opnu.labwork2.user.dto.UserResponse;
import ua.opnu.labwork2.user.model.User;
import ua.opnu.labwork2.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Tag(name = "Пошук", description = "Пошук задач та користувачів")
public class SearchController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Operation(summary = "Пошук задач")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> searchTasks(
            @RequestParam String query
    ) {

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        String q = query.trim().toLowerCase();

        List<TaskResponse> result = taskRepository.findAll().stream()
                .filter(task ->
                        contains(task.getTitle(), q) ||
                                contains(task.getDescription(), q)
                )
                .map(this::mapFullTask)
                .toList();

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Розширений пошук задач")
    @GetMapping("/tasks/advanced")
    public ResponseEntity<List<TaskResponse>> advancedSearch(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority
    ) {

        String q = (query != null && !query.trim().isEmpty())
                ? query.trim().toLowerCase()
                : null;

        List<TaskResponse> result = taskRepository.findAll().stream()
                .filter(task ->
                        (q == null ||
                                contains(task.getTitle(), q) ||
                                contains(task.getDescription(), q))
                                &&
                                (status == null || task.getStatus() == status)
                                &&
                                (priority == null || task.getPriority() == priority)
                )
                .map(this::mapShortTask)
                .toList();

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Пошук користувачів")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestParam String query
    ) {

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        String q = query.trim().toLowerCase();

        List<UserResponse> result = userRepository.findAll().stream()
                .filter(user ->
                        contains(user.getFirstName(), q) ||
                                contains(user.getLastName(), q) ||
                                contains(user.getEmail(), q)
                )
                .map(this::mapUser)
                .toList();

        return ResponseEntity.ok(result);
    }

    private boolean contains(String field, String query) {
        return field != null && field.toLowerCase().contains(query);
    }

    private TaskResponse mapFullTask(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),

                task.getUsers() != null
                        ? task.getUsers().stream().map(this::mapUser).toList()
                        : Collections.emptyList(),

                task.getTags() != null
                        ? task.getTags().stream()
                        .map(tag -> new TagResponse(
                                tag.getId(),
                                tag.getName(),
                                tag.getDescription()
                        ))
                        .toList()
                        : Collections.emptyList(),

                Collections.emptyList()
        );
    }

    private TaskResponse mapShortTask(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    private UserResponse mapUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}