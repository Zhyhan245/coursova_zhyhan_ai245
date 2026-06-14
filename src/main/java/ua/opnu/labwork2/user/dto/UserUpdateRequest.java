package ua.opnu.labwork2.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import ua.opnu.labwork2.user.model.UserRole;

@Schema(description = "Запит на оновлення користувача")
public record UserUpdateRequest(

        @NotBlank(message = "First name is mandatory")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        @Schema(
                description = "Ім'я користувача",
                example = "Ivan",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        @Schema(
                description = "Прізвище користувача",
                example = "Petrenko",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        @Schema(
                description = "Email користувача (унікальний)",
                example = "ivan.petrenko@gmail.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @NotNull(message = "Role is mandatory")
        @Schema(
                description = "Роль користувача (USER, ADMIN, MANAGER)",
                example = "USER",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UserRole role
) {}