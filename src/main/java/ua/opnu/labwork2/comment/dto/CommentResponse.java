package ua.opnu.labwork2.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Відповідь з даними коментаря")
public record CommentResponse(

        @Schema(
                description = "Унікальний ідентифікатор коментаря",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long id,

        @Schema(
                description = "Текст коментаря",
                example = "Ця задача потребує додаткового аналізу",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String text,

        @Schema(
                description = "Дата створення коментаря (генерується сервером)",
                example = "2026-06-01T12:30:00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDateTime createdDate
) {}