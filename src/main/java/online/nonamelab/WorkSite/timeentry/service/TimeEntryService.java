package online.nonamelab.WorkSite.timeentry.service;

import online.nonamelab.WorkSite.timeentry.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.timeentry.dto.TimeEntryResponse;

import java.time.LocalDate;
import java.util.List;

public interface TimeEntryService {

    TimeEntryResponse createOrGet(CreateTimeEntryRequest request);

    List<TimeEntryResponse> getMyEntries();

    TimeEntryResponse getByDate(LocalDate date);

    List<TimeEntryResponse> getAll();


}
