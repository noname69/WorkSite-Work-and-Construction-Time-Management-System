package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.TimeEntry;

import java.util.List;

public class TimeEntryMapper {

    public static TimeEntry toTimeEntry(CreateTimeEntryRequest request) {
        TimeEntry entry = new TimeEntry();

        entry.setDate(request.date());
        entry.setHours(request.hours());
        entry.setDistance(request.distance());
        entry.setStatus(request.status());

        return entry;
    }

    public static TimeEntryResponse toResponse(TimeEntry entry) {
        return new TimeEntryResponse(
                entry.getId(),
                entry.getDate(),
                entry.getHours(),
                entry.getDistance(),
                entry.getStatus(),
                entry.isLocked(),
                entry.getUser().getId(),
                entry.getSite().getId()
        );
    }

    public static List<TimeEntryResponse> toResponseList(List<TimeEntry> entries) {
        return entries.stream()
                .map(TimeEntryMapper::toResponse)
                .toList();
    }
}
