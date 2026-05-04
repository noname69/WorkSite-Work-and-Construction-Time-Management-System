package online.nonamelab.WorkSite.timeentry.dto;

import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;

public record CreateTimeEntryRequest(
//        @NotNull
//        LocalDate date,
//
//        @Min(0)
//        double hours,
//
//        @Min(0)
//        double distance,
//
//        @NotNull
//        TimeEntryStatus status,
//
//        @NotNull
//        Long siteId

        LocalDate date,

        TimeEntryStatus status,

        CreateTimeEntrySiteRequest site
) {
}
