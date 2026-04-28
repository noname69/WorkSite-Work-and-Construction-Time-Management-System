package online.nonamelab.WorkSite.dto;

import jakarta.validation.constraints.*;
import online.nonamelab.WorkSite.model.Role;

public record CreateUserRequest(

        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 100)
        String password,

        @NotNull
        Role role
) {
}
