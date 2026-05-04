package online.nonamelab.WorkSite.timeentry.dto;

import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;
import java.util.List;

public record TimeEntryResponse(
        Long id,
        LocalDate date,
        TimeEntryStatus status,
        boolean locked,
        Long userId,
        List<TimeEntrySiteResponse> sites
) {

}
