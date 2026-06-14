package ua.opnu.labwork2.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.comment.dto.CommentResponse;
import ua.opnu.labwork2.tag.dto.TagResponse;
import ua.opnu.labwork2.task.model.TaskPriority;
import ua.opnu.labwork2.task.model.TaskStatus;
import ua.opnu.labwork2.user.dto.UserResponse;

import java.util.List;

@Schema(description = "Відповідь з даними задачі")
public record TaskResponse(

        @Schema(description = "ID задачі", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Назва задачі", example = "Implement Swagger documentation")
        String title,

        @Schema(description = "Опис задачі", example = "Додати Swagger документацію до REST API")
        String description,

        @Schema(description = "Статус задачі", example = "IN_PROGRESS")
        TaskStatus status,

        @Schema(description = "Пріоритет задачі", example = "HIGH")
        TaskPriority priority,

        @Schema(description = "Виконавці задачі")
        List<UserResponse> users,

        @Schema(description = "Теги задачі")
        List<TagResponse> tags,

        @Schema(description = "Коментарі до задачі")
        List<CommentResponse> comments
) {}