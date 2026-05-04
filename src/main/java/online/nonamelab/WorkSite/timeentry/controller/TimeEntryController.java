package online.nonamelab.WorkSite.timeentry.controller;

import online.nonamelab.WorkSite.timeentry.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.timeentry.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.timeentry.service.TimeEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@PreAuthorize("hasAnyRole('WORKER','MANAGER')")
@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @PostMapping
    public ResponseEntity<TimeEntryResponse> createOrGet(
            @RequestBody CreateTimeEntryRequest request
            ) {

        TimeEntryResponse response = timeEntryService.createOrGet(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    public ResponseEntity<List<TimeEntryResponse>> getMyEntries(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {

        return ResponseEntity.ok(
                timeEntryService.getMyEntries(from, to)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TimeEntryResponse>> getByUserAndMonth(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        return ResponseEntity.ok(
                timeEntryService.getByUserAndMonth(userId, year, month)
        );
    }
}
