package online.nonamelab.WorkSite.timeentry.dto;

import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;

public record CreateTimeEntryRequest(
        LocalDate date,
        TimeEntryStatus status,
        CreateTimeEntrySiteRequest site
) {
}
