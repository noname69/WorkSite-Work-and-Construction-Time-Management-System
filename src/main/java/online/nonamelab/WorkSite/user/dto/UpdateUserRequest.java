package online.nonamelab.WorkSite.user.dto;

import online.nonamelab.WorkSite.model.Role;

public record UpdateUserRequest(
        String name,
        String email,
        Role role
) {
}
