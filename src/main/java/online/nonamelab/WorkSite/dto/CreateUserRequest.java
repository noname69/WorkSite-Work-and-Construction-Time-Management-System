package online.nonamelab.WorkSite.dto;

import jakarta.validation.constraints.*;
import online.nonamelab.WorkSite.model.Role;

public record CreateUserRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 100)
        String name,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100)
        String password,

        @NotNull(message = "Role is required")
        Role role
) {
}
