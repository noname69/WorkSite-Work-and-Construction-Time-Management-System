package online.nonamelab.WorkSite.controller;

import jakarta.validation.Valid;
import online.nonamelab.WorkSite.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.service.TimeEntryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'WORKER')")
    @GetMapping
    public List<TimeEntryResponse> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return timeEntryService.getAll(userId, year, month);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'WORKER')")
    @PostMapping
    public TimeEntryResponse create(@RequestBody @Valid CreateTimeEntryRequest request) {
        return timeEntryService.create(request);
    }


}
