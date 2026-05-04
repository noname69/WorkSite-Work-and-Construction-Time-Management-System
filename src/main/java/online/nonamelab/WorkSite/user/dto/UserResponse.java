package online.nonamelab.WorkSite.user.dto;

import online.nonamelab.WorkSite.model.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role
) {
}
