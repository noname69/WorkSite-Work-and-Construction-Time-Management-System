package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.model.TimeEntryStatus;

import java.time.LocalDate;
import java.util.List;

public interface TimeEntryService {

    TimeEntryResponse createOrGet(CreateTimeEntryRequest request);

    List<TimeEntryResponse> getMyEntries();

    TimeEntryResponse getByDate(LocalDate date);

    List<TimeEntryResponse> getAll();


}
