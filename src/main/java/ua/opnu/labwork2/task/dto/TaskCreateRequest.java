package ua.opnu.labwork2.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.opnu.labwork2.task.model.TaskPriority;
import ua.opnu.labwork2.task.model.TaskStatus;

@Schema(description = "Запит на створення задачі")
public record TaskCreateRequest(

        @NotBlank
        @Size(min = 2, max = 150)
        @Schema(
                description = "Назва задачі",
                example = "Implement Swagger documentation",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String title,

        @NotBlank
        @Size(min = 10, max = 2000)
        @Schema(
                description = "Опис задачі",
                example = "Необхідно додати Swagger документацію до REST API",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description,

        @NotNull
        @Schema(
                description = "Статус задачі",
                example = "IN_PROGRESS",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        TaskStatus status,

        @NotNull
        @Schema(
                description = "Пріоритет задачі",
                example = "HIGH",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        TaskPriority priority,

        @NotNull
        @Schema(
                description = "ID проєкту, до якого належить задача",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long projectId
) {}