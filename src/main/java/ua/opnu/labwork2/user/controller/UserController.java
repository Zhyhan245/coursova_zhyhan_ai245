package ua.opnu.labwork2.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.task.dto.TaskResponse;
import ua.opnu.labwork2.user.dto.UserCreateRequest;
import ua.opnu.labwork2.user.dto.UserResponse;
import ua.opnu.labwork2.user.dto.UserUpdateRequest;
import ua.opnu.labwork2.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(
        name = "Користувачі",
        description = "Управління користувачами системи та їх задачами"
)
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Створити нового користувача",
            description = "Створює нового користувача. Email має бути унікальним.")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    @GetMapping
    @Operation(summary = "Отримати список користувачів",
            description = "Повертає список всіх користувачів системи")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати інформацію про користувача",
            description = "Повертає користувача за його ID")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані користувача",
            description = "Оновлює дані користувача (ім'я, email, роль)")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити користувача",
            description = "Видаляє користувача з системи")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Отримати задачі користувача",
            description = "Повертає всі задачі, призначені користувачу")
    public ResponseEntity<List<TaskResponse>> getUserTasks(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTasksByUserId(id));
    }
}