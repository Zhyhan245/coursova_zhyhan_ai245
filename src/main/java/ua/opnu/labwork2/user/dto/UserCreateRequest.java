package ua.opnu.labwork2.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ua.opnu.labwork2.user.model.UserRole;

@Schema(description = "Запит на створення користувача")
public record UserCreateRequest(

        @NotBlank(message = "First name is mandatory")
        @Schema(
                description = "Ім'я користувача",
                example = "Ivan"
        )
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        @Schema(
                description = "Прізвище користувача",
                example = "Petrenko"
        )
        String lastName,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        @Schema(
                description = "Email користувача (унікальний)",
                example = "ivan.petrenko@gmail.com"
        )
        String email,

        @NotNull(message = "Role is mandatory")
        @Schema(
                description = "Роль користувача (USER, ADMIN, MANAGER)",
                example = "USER"
        )
        UserRole role
) {}