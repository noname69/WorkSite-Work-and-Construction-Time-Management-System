package online.nonamelab.WorkSite.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateMeRequest(
//        String name,

        @Size(min = 2, max = 50)
        String firstName,

        @Size(min = 2, max = 50)
        String lastName,

        @Email
        String email,

        String phoneNumber

        ) {}
