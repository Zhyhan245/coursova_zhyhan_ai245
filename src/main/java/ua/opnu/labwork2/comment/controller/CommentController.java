package ua.opnu.labwork2.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.opnu.labwork2.comment.dto.CommentCreateRequest;
import ua.opnu.labwork2.comment.dto.CommentResponse;
import ua.opnu.labwork2.comment.dto.CommentUpdateRequest;
import ua.opnu.labwork2.comment.service.CommentService;
import ua.opnu.labwork2.exception.ApiErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(
        name = "Коментарі",
        description = "Управління коментарями до задач у системі проєктного менеджменту"
)
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Додати коментар",
            description = """
                    Створює новий коментар до задачі.

                    Бізнес-правила:
                    - коментар прив’язується до taskId
                    - текст коментаря не може бути порожнім
                    - дата створення генерується автоматично сервером
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Коментар створено",
                    content = @Content(schema = @Schema(implementation = CommentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Помилка валідації даних",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задачу не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.create(request));
    }

    @Operation(
            summary = "Отримати список коментарів",
            description = "Повертає список усіх коментарів у системі"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список коментарів отримано",
            content = @Content(schema = @Schema(implementation = CommentResponse.class))
    )
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @Operation(
            summary = "Отримати інформацію про коментар",
            description = "Повертає коментар за його унікальним ідентифікатором"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Коментар знайдено",
                    content = @Content(schema = @Schema(implementation = CommentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Коментар не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @Operation(
            summary = "Оновити коментар",
            description = "Оновлює текст коментаря"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Коментар оновлено",
                    content = @Content(schema = @Schema(implementation = CommentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоректні дані",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Коментар не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        return ResponseEntity.ok(commentService.update(id, request));
    }

    @Operation(
            summary = "Видалити коментар",
            description = "Видаляє коментар з системи за ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Коментар видалено"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}