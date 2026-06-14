package ua.opnu.labwork2.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.opnu.labwork2.exception.ApiErrorResponse;

import ua.opnu.labwork2.task.dto.TaskCreateRequest;
import ua.opnu.labwork2.task.dto.TaskResponse;
import ua.opnu.labwork2.task.dto.TaskUpdateRequest;
import ua.opnu.labwork2.task.service.TaskService;

import ua.opnu.labwork2.comment.dto.CommentResponse;
import ua.opnu.labwork2.comment.service.CommentService;
import ua.opnu.labwork2.user.dto.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(
        name = "Задачі",
        description = "Управління задачами, виконавцями, пріоритетами та тегами"
)
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    @PostMapping
    @Operation(
            summary = "Створити задачу",
            description = "Створює нову задачу з прив’язкою до проєкту"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Задачу створено",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Помилка валідації",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.create(request));
    }

    @GetMapping
    @Operation(
            summary = "Отримати всі задачі",
            description = "Повертає список задач з підтримкою пагінації"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Пагінований список задач",
            content = @Content(schema = @Schema(implementation = Page.class))
    )
    public ResponseEntity<Page<TaskResponse>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(taskService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Отримати задачу за ID",
            description = "Повертає задачу по її унікальному ідентифікатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Задачу знайдено",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TaskResponse> getById(
            @Parameter(description = "ID задачі", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/project/{projectId}")
    @Operation(
            summary = "Отримати задачі за ID проєкту",
            description = "Повертає список усіх задач, які належать конкретному проєкту"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список задач проєкту отримано",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = TaskResponse.class)
                    ))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Проєкт не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<List<TaskResponse>> getTasksByProject(
            @Parameter(description = "ID проєкту", required = true)
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(taskService.findTasksByProjectId(projectId));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Оновити задачу",
            description = "Оновлює дані задачі (назва, опис, статус, пріоритет)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Задачу оновлено",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Помилка валідації",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TaskResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request
    ) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Видалити задачу",
            description = "Видаляє задачу з системи без можливості відновлення"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Задачу видалено"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comments")
    @Operation(
            summary = "Отримати коментарі задачі",
            description = "Повертає всі коментарі, прив’язані до задачі"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список коментарів",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = CommentResponse.class)
                    ))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<List<CommentResponse>> getCommentsByTaskId(
            @Parameter(description = "ID задачі", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(commentService.findCommentsByTaskId(id));
    }

    @PostMapping("/{id}/tags/{tagId}")
    @Operation(
            summary = "Додати тег до задачі",
            description = "Прив’язує тег до задачі за її ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тег додано до задачі"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу або тег не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TaskResponse> addTagToTask(
            @PathVariable Long id,
            @PathVariable Long tagId
    ) {
        return ResponseEntity.ok(taskService.addTag(id, tagId));
    }

    @DeleteMapping("/{id}/tags/{tagId}")
    @Operation(
            summary = "Видалити тег із задачі",
            description = "Відв’язує тег від задачі"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тег видалено із задачі"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу або тег не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TaskResponse> removeTagFromTask(
            @PathVariable Long id,
            @PathVariable Long tagId
    ) {
        return ResponseEntity.ok(taskService.removeTag(id, tagId));
    }

    @GetMapping("/{id}/users")
    @Operation(
            summary = "Отримати виконавців задачі",
            description = "Повертає список користувачів, які призначені на задачу"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список виконавців задачі",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<List<UserResponse>> getTaskUsers(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(taskService.getTaskUsers(id));
    }
}