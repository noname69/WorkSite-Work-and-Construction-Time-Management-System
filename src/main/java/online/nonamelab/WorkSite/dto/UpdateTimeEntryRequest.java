package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;

public record UpdateTimeEntryRequest(
        LocalDate date,
        Double hours,
        Double distance,
        TimeEntryStatus status
) {
}
