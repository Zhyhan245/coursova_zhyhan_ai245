package ua.opnu.labwork2.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запит на створення нового тегу")
public record TagCreateRequest(

        @NotBlank
        @Size(min = 2, max = 150)
        @Schema(
                description = "Назва тегу (унікальна)",
                example = "backend",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,

        @NotBlank
        @Size(min = 10, max = 2000)
        @Schema(
                description = "Опис тегу",
                example = "Тег використовується для класифікації задач, пов'язаних з backend розробкою",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String description
) {}