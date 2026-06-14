package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.opnu.labwork2.project.repository.ProjectRepository;
import ua.opnu.labwork2.task.model.TaskStatus;
import ua.opnu.labwork2.task.repository.TaskRepository;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
@Tag(
        name = "Аналітика",
        description = "Статистика проєктів, задач та їх стану"
)
public class AnalyticsController {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @GetMapping("/projects/count")
    @Operation(
            summary = "Кількість проєктів",
            description = "Повертає загальну кількість проєктів у системі"
    )
    @ApiResponse(responseCode = "200", description = "Успішно отримано")
    public ResponseEntity<Map<String, Long>> projectsCount() {
        return ResponseEntity.ok(
                Map.of("кількістьПроєктів", projectRepository.count())
        );
    }

    @GetMapping("/tasks/count")
    @Operation(
            summary = "Кількість задач",
            description = "Повертає загальну кількість задач у системі"
    )
    @ApiResponse(responseCode = "200", description = "Успішно отримано")
    public ResponseEntity<Map<String, Long>> tasksCount() {
        return ResponseEntity.ok(
                Map.of("кількістьЗадач", taskRepository.count())
        );
    }

    @GetMapping("/tasks/active")
    @Operation(
            summary = "Активні задачі",
            description = "Повертає кількість задач зі статусом IN_PROGRESS"
    )
    @ApiResponse(responseCode = "200", description = "Успішно отримано")
    public ResponseEntity<Map<String, Long>> activeTasks() {
        return ResponseEntity.ok(
                Map.of(
                        "активніЗадачі",
                        taskRepository.countByStatus(TaskStatus.IN_PROGRESS)
                )
        );
    }

    @GetMapping("/tasks/completed")
    @Operation(
            summary = "Завершені задачі",
            description = "Повертає кількість задач зі статусом DONE"
    )
    @ApiResponse(responseCode = "200", description = "Успішно отримано")
    public ResponseEntity<Map<String, Long>> completedTasks() {
        return ResponseEntity.ok(
                Map.of(
                        "завершеніЗадачі",
                        taskRepository.countByStatus(TaskStatus.DONE)
                )
        );
    }

    @GetMapping("/tasks/by-priority")
    @Operation(
            summary = "Статистика пріоритетів",
            description = "Групування задач за пріоритетами"
    )
    @ApiResponse(responseCode = "200", description = "Успішно отримано")
    public ResponseEntity<Map<String, Object>> byPriority() {
        return ResponseEntity.ok(
                Map.of("статистикаПріоритетів", taskRepository.countByPriority())
        );
    }
}