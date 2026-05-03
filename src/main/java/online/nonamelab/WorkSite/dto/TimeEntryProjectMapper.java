package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.TimeEntryProject;

public class TimeEntryProjectMapper {
    public static TimeEntryProject toTimeEntryProject(CreateTimeEntryProjectRequest request) {
        TimeEntryProject project = new TimeEntryProject();

        project.setHours(request.hours());
        project.setDistance(request.distance());

        return project;
    }

    public static TimeEntryProjectResponse toResponse(TimeEntryProject project) {
        return new TimeEntryProjectResponse(
                project.getId(),
                project.getHours(),
                project.getDistance(),
                project.getConstructionSite().getId(),
                project.getConstructionSite().getName()
        );
    }
}
