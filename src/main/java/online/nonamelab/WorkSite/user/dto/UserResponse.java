package online.nonamelab.WorkSite.user.dto;

import online.nonamelab.WorkSite.model.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
//        String firstName,
//        String lastName,

        String email,
//        String phoneNumber,

        Role role

//        boolean deleted,

//        LocalDateTime createdAt,
//        LocalDateTime updatedAt,
//        LocalDateTime lastLoginAt
) {
}
