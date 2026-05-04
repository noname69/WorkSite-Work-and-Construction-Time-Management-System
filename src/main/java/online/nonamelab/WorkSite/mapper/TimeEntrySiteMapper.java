package online.nonamelab.WorkSite.mapper;

import online.nonamelab.WorkSite.model.TimeEntrySite;
import online.nonamelab.WorkSite.timeentry.dto.CreateTimeEntrySiteRequest;
import online.nonamelab.WorkSite.timeentry.dto.TimeEntrySiteResponse;

public class TimeEntrySiteMapper {
    public static TimeEntrySite toTimeEntryProject(CreateTimeEntrySiteRequest request) {
        TimeEntrySite project = new TimeEntrySite();

        project.setHours(request.hours());
        project.setDistance(request.distance());

        return project;
    }

    public static TimeEntrySiteResponse toResponse(TimeEntrySite project) {
        return new TimeEntrySiteResponse(
                project.getId(),
                project.getHours(),
                project.getDistance(),
                project.getConstructionSite().getId(),
                project.getConstructionSite().getName()
        );
    }
}
