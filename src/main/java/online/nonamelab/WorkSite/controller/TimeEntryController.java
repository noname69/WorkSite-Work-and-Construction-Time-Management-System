package online.nonamelab.WorkSite.controller;

import online.nonamelab.WorkSite.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.service.TimeEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
