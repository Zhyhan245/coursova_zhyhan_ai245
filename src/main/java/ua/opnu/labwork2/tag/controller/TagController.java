package ua.opnu.labwork2.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.opnu.labwork2.exception.ApiErrorResponse;
import ua.opnu.labwork2.tag.dto.TagCreateRequest;
import ua.opnu.labwork2.tag.dto.TagResponse;
import ua.opnu.labwork2.tag.dto.TagUpdateRequest;
import ua.opnu.labwork2.tag.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Tag(
        name = "Теги",
        description = "Управління тегами для задач у системі"
)
public class TagController {

    private final TagService tagService;

    @PostMapping
    @Operation(
            summary = "Створити новий тег",
            description = "Створює новий тег у системі. Назва тегу повинна бути унікальною."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Тег створено",
                    content = @Content(schema = @Schema(implementation = TagResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Помилка валідації",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Дублікат тегу",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TagResponse> create(@Valid @RequestBody TagCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(request));
    }

    @GetMapping
    @Operation(
            summary = "Отримати список тегів",
            description = "Повертає список усіх тегів"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список тегів",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = TagResponse.class)
            ))
    )
    public ResponseEntity<List<TagResponse>> getAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати інформацію про тег")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Тег знайдено",
                    content = @Content(schema = @Schema(implementation = TagResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Тег не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TagResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Оновити тег",
            description = "Оновлює тег. Назва повинна залишатись унікальною."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Тег оновлено",
                    content = @Content(schema = @Schema(implementation = TagResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Помилка валідації",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Тег не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<TagResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TagUpdateRequest request
    ) {
        return ResponseEntity.ok(tagService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Видалити тег",
            description = "Видаляє тег. Неможливо видалити, якщо він використовується в задачах."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Тег видалено"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Тег не знайдено",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Тег використовується в задачах",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}