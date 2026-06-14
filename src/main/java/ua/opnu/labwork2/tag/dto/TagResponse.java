package ua.opnu.labwork2.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Відповідь з даними тегу")
public record TagResponse(

        @Schema(
                description = "ID тегу",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long id,

        @Schema(
                description = "Назва тегу",
                example = "backend",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,

        @Schema(
                description = "Опис тегу",
                example = "Тег використовується для класифікації задач, пов'язаних з backend розробкою"
        )
        String description
) {}