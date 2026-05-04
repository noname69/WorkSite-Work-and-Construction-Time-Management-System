package online.nonamelab.WorkSite.mapper;

import online.nonamelab.WorkSite.model.TimeEntry;
import online.nonamelab.WorkSite.timeentry.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.timeentry.dto.TimeEntryResponse;

import java.util.List;

public class TimeEntryMapper {

    public static TimeEntry toTimeEntry(CreateTimeEntryRequest request) {
        TimeEntry entry = new TimeEntry();

        entry.setDate(request.date());
//        entry.setHours(request.hours());
//        entry.setDistance(request.distance());
        entry.setStatus(request.status());

        return entry;
    }

    public static TimeEntryResponse toResponse(TimeEntry entry) {
        return new TimeEntryResponse(
                entry.getId(),
                entry.getDate(),
//                entry.getHours(),
//                entry.getDistance(),
                entry.getStatus(),
                entry.isLocked(),
                entry.getUser().getId(),
//                entry.getSite().getId()
                entry.getProjects().stream()
                        .map(TimeEntrySiteMapper::toResponse)
                        .toList()
        );
    }

    public static List<TimeEntryResponse> toResponseList(List<TimeEntry> entries) {
        return entries.stream()
                .map(TimeEntryMapper::toResponse)
                .toList();
    }
}
