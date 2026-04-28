package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.Role;

public record UpdateUserRequest(
        Long id,
        String name,
        String email,
        String password,
        Role role
) {
}
