package ua.opnu.labwork2.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import ua.opnu.labwork2.project.dto.ProjectCreateRequest;
import ua.opnu.labwork2.project.dto.ProjectResponse;
import ua.opnu.labwork2.project.dto.ProjectUpdateRequest;
import ua.opnu.labwork2.project.service.ProjectService;
import ua.opnu.labwork2.task.dto.TaskResponse;
import ua.opnu.labwork2.exception.ApiErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(
        name = "Проєкти",
        description = "Управління проєктами (CRUD операції та задачі в межах проєкту)"
)
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Створити новий проєкт", description = "Назва проєкту повинна бути унікальною")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Проєкт створено",
                    content = @Content(schema = @Schema(implementation = ProjectResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Помилка валідації",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409",
                    description = "Проєкт вже існує",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.create(request));
    }

    @GetMapping
    @Operation(summary = "Отримати список проєктів")
    @ApiResponse(responseCode = "200",
            description = "Список проєктів",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjectResponse.class))))
    public ResponseEntity<List<ProjectResponse>> getAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати інформацію про проєкт")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Проєкт знайдено",
                    content = @Content(schema = @Schema(implementation = ProjectResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ProjectResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Оновити дані проєкту",
            description = "Оновлює дані проєкту. Назва повинна залишатись унікальною."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Проєкт оновлено",
                    content = @Content(schema = @Schema(implementation = ProjectResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Проєкт не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Конфлікт даних",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<ProjectResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProjectUpdateRequest request
    ) {
        return ResponseEntity.ok(projectService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Видалити проєкт",
            description = "Видаляє проєкт. Якщо є задачі — можливе обмеження через бізнес-правила."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Проєкт видалено"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Проєкт не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    @Operation(
            summary = "Отримати задачі проєкту",
            description = "Повертає всі задачі, що належать конкретному проєкту"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список задач проєкту",
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
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findTasksByProjectId(id));
    }
}