package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.Role;

public record CreateUserRequest(
        String name,
        String email,
        String password,
        Role role
) {
}
