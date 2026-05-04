package online.nonamelab.WorkSite.timeentry.service;

import online.nonamelab.WorkSite.timeentry.dto.CreateTimeEntrySiteRequest;
import online.nonamelab.WorkSite.timeentry.dto.TimeEntrySiteResponse;

public interface TimeEntrySiteService {
    TimeEntrySiteResponse addSiteToEntry(Long timeEntryId, CreateTimeEntrySiteRequest request);

    TimeEntrySiteResponse updateSite(Long siteId, CreateTimeEntrySiteRequest request);

    void deleteSite(Long siteId);

    TimeEntrySiteResponse getById(Long siteId);
}
