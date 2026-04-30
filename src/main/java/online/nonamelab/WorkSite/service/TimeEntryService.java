package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.dto.UpdateTimeEntryRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface TimeEntryService {
    List<TimeEntryResponse> getAll(Long userId, int year, int month);

    TimeEntryResponse getById(Long id);

    TimeEntryResponse create(CreateTimeEntryRequest request);

    TimeEntryResponse update(Long id, UpdateTimeEntryRequest request);

    void delete(Long id);

//    UserMonthlySummaryResponse getUserMonthlySummary(
//            Long userId,
//            int year,
//            int month
//    );
//public record UserMonthlySummaryResponse(
//
//        Long userId,
//        int year,
//        int month,
//
//        double totalHours,
//        double totalOvertime,
//        double totalDistance,
//
//        long workingDays,
//        long vacationDays,
//        long sickDays
//) {}

//    List<TimeEntryResponse> getByUser(Long userId);
//
//    List<TimeEntryResponse> getBySite(Long siteId);
//
//    List<TimeEntryResponse> getByDateRange(LocalDate start, LocalDate end);
//
//    List<TimeEntryResponse> getByUserAndDateRange(
//            Long userId,
//            LocalDate start,
//            LocalDate end
//    );
//
//    List<TimeEntryResponse> getBySiteAndDateRange(
//            Long siteId,
//            LocalDate start,
//            LocalDate end
//    );
//
//    List<TimeEntryResponse> getMyEntries();
//
//    List<TimeEntryResponse> getMyEntriesByDateRange(
//            LocalDate start,
//            LocalDate end
//    );
//
//    double getTotalHoursByUser(Long userId, LocalDate start, LocalDate end);
//
//    double getTotalOvertimeByUser(Long userId, LocalDate start, LocalDate end);
}
