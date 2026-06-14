package ua.opnu.labwork2.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Запит на створення коментаря до задачі (Task)")
public record CommentCreateRequest(

        @NotBlank(message = "Comment text is mandatory")
        @Size(
                min = 10,
                max = 2000,
                message = "Comment text must be between 10 and 2000 characters"
        )
        @Schema(
                description = "Текст коментаря",
                example = "Ця задача потребує додаткової перевірки логіки",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String text,

        @NotNull(message = "Task ID is mandatory")
        @Schema(
                description = "Ідентифікатор задачі, до якої додається коментар",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long taskId
) {}