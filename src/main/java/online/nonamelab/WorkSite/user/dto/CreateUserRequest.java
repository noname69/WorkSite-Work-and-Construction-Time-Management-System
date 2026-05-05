package online.nonamelab.WorkSite.user.dto;

import jakarta.validation.constraints.*;
import online.nonamelab.WorkSite.model.Role;

public record CreateUserRequest(

//        @NotBlank(message = "Name is required")
//        @Size(min = 3, max = 100)
//        String name,

        @NotBlank(message = "First name is required")
        @Size(min = 1, max = 50)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 1, max = 50)
        String lastName,

        String phoneNumber,

        @NotBlank(message = "Email is required")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "Email must be valid (example: user@domain.com)"
        )
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotNull(message = "Role is required")
        Role role
) {
}
