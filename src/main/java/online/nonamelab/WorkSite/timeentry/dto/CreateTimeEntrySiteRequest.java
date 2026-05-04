package online.nonamelab.WorkSite.timeentry.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTimeEntrySiteRequest(

        @NotNull(message = "Construction site is required")
        Long constructionSiteId,

        @Positive(message = "Hours must be greater than 0")
        double hours,

        @Positive(message = "Distance must be greater than 0")
        double distance
) {
}
