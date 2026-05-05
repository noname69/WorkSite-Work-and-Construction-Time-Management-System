package online.nonamelab.WorkSite.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
