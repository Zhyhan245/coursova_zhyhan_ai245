package ua.opnu.labwork2.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.user.model.UserRole;

@Schema(description = "Відповідь з даними користувача")
public record UserResponse(

        @Schema(
                description = "Унікальний ідентифікатор користувача",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long id,

        @Schema(
                description = "Ім'я користувача",
                example = "Ivan",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String firstName,

        @Schema(
                description = "Прізвище користувача",
                example = "Petrenko",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @Schema(
                description = "Електронна пошта користувача",
                example = "ivan.petrenko@gmail.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "Роль користувача (USER, ADMIN, MANAGER)",
                example = "USER",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UserRole role
) {}