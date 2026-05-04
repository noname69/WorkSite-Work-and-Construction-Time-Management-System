package online.nonamelab.WorkSite.user.dto;

import online.nonamelab.WorkSite.model.Role;

public record AuthResponse(
        String tokenType,
        String accessToken,
        String email,
        Role role
) {
}
