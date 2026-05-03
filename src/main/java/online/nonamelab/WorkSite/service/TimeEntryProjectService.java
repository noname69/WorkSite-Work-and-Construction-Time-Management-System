package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateTimeEntryProjectRequest;
import online.nonamelab.WorkSite.dto.TimeEntryProjectResponse;
import online.nonamelab.WorkSite.model.TimeEntryProject;

public interface TimeEntryProjectService {
    TimeEntryProjectResponse addProjectToEntry(Long timeEntryId, CreateTimeEntryProjectRequest request);

    TimeEntryProjectResponse updateProject(Long projectId, CreateTimeEntryProjectRequest request);

    void deleteProject(Long projectId);

    TimeEntryProjectResponse getById(Long projectId);
}
