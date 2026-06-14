package ua.opnu.labwork2.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(
        name = "ApiErrorResponse",
        description = "Стандартна структура відповіді про помилку API"
)
public record ApiErrorResponse(

        @Schema(
                description = "Час виникнення помилки",
                example = "2026-06-01T20:15:30"
        )
        LocalDateTime timestamp,

        @Schema(
                description = "HTTP статус код",
                example = "400"
        )
        int status,

        @Schema(
                description = "Коротка назва HTTP помилки",
                example = "BAD_REQUEST"
        )
        String error,

        @Schema(
                description = "Детальне повідомлення про помилку",
                example = "Validation failed"
        )
        String message,

        @Schema(
                description = "Шлях запиту, де сталася помилка",
                example = "/tasks"
        )
        String path,

        @Schema(
                description = "Помилки валідації полів (ключ = поле, значення = помилка)"
        )
        Map<String, String> errors
) {}
