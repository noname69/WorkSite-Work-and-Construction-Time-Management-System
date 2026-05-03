package online.nonamelab.WorkSite.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTimeEntryProjectRequest(

        @NotNull(message = "Construction site is required")
        Long constructionSiteId,

        @Positive(message = "Hours must be greater than 0")
        double hours,

        @Positive(message = "Distance must be greater than 0")
        double distance
) {
}
