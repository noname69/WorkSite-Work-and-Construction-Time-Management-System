package online.nonamelab.WorkSite.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import online.nonamelab.WorkSite.model.Role;

public record UpdateUserRequest(
//        String name,
        @Size(min = 1, max = 50)
        String firstName,

        @Size(min = 1, max = 50)
        String lastName,

        @Email
        String email,

        String phoneNumber,

        @NotNull(message = "Role is required")
        Role role
) {
}
