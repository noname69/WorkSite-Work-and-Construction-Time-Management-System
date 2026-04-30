package online.nonamelab.WorkSite.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;

public record CreateTimeEntryRequest(
        @NotNull
        LocalDate date,

        @Min(0)
        double hours,

        @Min(0)
        double distance,

        @NotNull
        TimeEntryStatus status,

        @NotNull
        Long siteId
) {
}
