package online.nonamelab.WorkSite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import online.nonamelab.WorkSite.model.Priority;
import online.nonamelab.WorkSite.model.SiteStatus;

import java.time.LocalDate;

public record CreateSiteRequest(
        @NotBlank
        String name,

        String description,

        @NotNull
        SiteStatus status,

        @NotNull
        Priority priority,

        LocalDate startDate,
        LocalDate endDate,

        @NotNull
        Long managerId
) {
}
