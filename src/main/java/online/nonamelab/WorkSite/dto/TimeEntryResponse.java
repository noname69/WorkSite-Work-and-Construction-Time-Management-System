package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;
import java.util.List;

public record TimeEntryResponse(
        Long id,
        LocalDate date,
//        double hours,
//        double distance,
        TimeEntryStatus status,
        boolean locked,

//        Long userId,
//        Long siteId
        Long userId,

        List<TimeEntryProjectResponse> projects
) {

}
