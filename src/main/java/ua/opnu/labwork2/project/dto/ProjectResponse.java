package ua.opnu.labwork2.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Відповідь з даними про проєкт")
public record ProjectResponse(

        @Schema(
                description = "Унікальний ідентифікатор проєкту",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long id,

        @Schema(
                description = "Назва проєкту",
                example = "Task Management System",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,

        @Schema(
                description = "Опис проєкту",
                example = "Backend система для управління задачами",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description,

        @Schema(
                description = "Дата створення проєкту",
                example = "2026-06-01",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate createdDate
) {}