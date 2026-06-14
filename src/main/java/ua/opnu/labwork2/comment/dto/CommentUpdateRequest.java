package ua.opnu.labwork2.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запит на оновлення коментаря")
public record CommentUpdateRequest(

        @NotBlank(message = "Text is mandatory")
        @Size(
                min = 10,
                max = 2000,
                message = "Text must be between 10 and 2000 characters"
        )
        @Schema(
                description = "Оновлений текст коментаря",
                example = "Оновлений коментар: задача вже перевірена",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String text
) {}