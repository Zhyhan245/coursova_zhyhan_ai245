package ua.opnu.labwork2.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.opnu.labwork2.task.model.TaskPriority;
import ua.opnu.labwork2.task.model.TaskStatus;

@Schema(description = "Запит на оновлення задачі")
public record TaskUpdateRequest(

        @NotBlank
        @Size(min = 2, max = 150)
        @Schema(
                description = "Назва задачі",
                example = "Updated task title",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String title,

        @NotBlank
        @Size(min = 10, max = 2000)
        @Schema(
                description = "Опис задачі",
                example = "Оновлений опис задачі",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description,

        @NotNull
        @Schema(
                description = "Статус задачі",
                example = "DONE",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        TaskStatus status,

        @NotNull
        @Schema(
                description = "Пріоритет задачі",
                example = "HIGH",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        TaskPriority priority
) {}
