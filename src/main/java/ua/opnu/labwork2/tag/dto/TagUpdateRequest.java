package ua.opnu.labwork2.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запит на оновлення тегу")
public record TagUpdateRequest(

        @NotBlank
        @Size(min = 2, max = 150)
        @Schema(
                description = "Назва тегу",
                example = "backend-updated",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,

        @NotBlank
        @Size(min = 10, max = 2000)
        @Schema(
                description = "Опис тегу",
                example = "Оновлений опис тегу для backend задач",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description
) {}