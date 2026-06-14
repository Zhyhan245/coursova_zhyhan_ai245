package ua.opnu.labwork2.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запит на створення нового проєкту")
public record ProjectCreateRequest(

        @NotBlank(message = "Project name is mandatory")
        @Size(
                min = 2,
                max = 150,
                message = "Project name must be between 2 and 150 characters"
        )
        @Schema(
                description = "Назва проєкту",
                example = "Task Management System",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,

        @NotBlank(message = "Project description is mandatory")
        @Size(
                min = 10,
                max = 2000,
                message = "Project description must be between 10 and 2000 characters"
        )
        @Schema(
                description = "Детальний опис проєкту",
                example = "Backend система для управління проєктами та задачами команди розробників",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description
) {}