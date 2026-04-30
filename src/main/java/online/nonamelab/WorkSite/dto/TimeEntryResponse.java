package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;

public record TimeEntryResponse(
        Long id,
        LocalDate date,
        double hours,
        double distance,
        TimeEntryStatus status,
        boolean locked,

        Long userId,
        Long siteId
) {

}
