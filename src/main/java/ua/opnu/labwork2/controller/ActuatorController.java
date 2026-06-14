package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/actuator")
@Tag(
        name = "Моніторинг системи",
        description = "Ендпоінти для перевірки стану системи та метрик"
)
public class ActuatorController {

    @GetMapping("/health")
    @Operation(
            summary = "Перевірка стану системи",
            description = "Повертає статус роботи API та поточний час"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Стан системи отримано",
            content = @Content(schema = @Schema(implementation = Map.class))
    )
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(
                Map.of(
                        "status", "UP",
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    @GetMapping("/metrics")
    @Operation(
            summary = "Метрики системи",
            description = "Повертає базову інформацію про стан та конфігурацію API"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Метрики отримано",
            content = @Content(schema = @Schema(implementation = Map.class))
    )
    public ResponseEntity<Map<String, Object>> metrics() {
        return ResponseEntity.ok(
                Map.of(
                        "application", "project-management-api",
                        "requests", "enabled",
                        "status", "available"
                )
        );
    }

    @GetMapping("/prometheus")
    @Operation(
            summary = "Prometheus метрики",
            description = "Повертає метрики у форматі Prometheus"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Prometheus метрики",
            content = @Content(mediaType = "text/plain")
    )
    public ResponseEntity<String> prometheus() {
        return ResponseEntity.ok(
                "# HELP http_requests_total Total HTTP requests\n" +
                        "# TYPE http_requests_total counter\n" +
                        "http_requests_total{service=\"project-api\"} 42"
        );
    }
}