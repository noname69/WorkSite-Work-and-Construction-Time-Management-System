package online.nonamelab.WorkSite.controller;

import jakarta.validation.Valid;
import online.nonamelab.WorkSite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.dto.SiteResponse;
import online.nonamelab.WorkSite.dto.UserResponse;
import online.nonamelab.WorkSite.service.ConstructionSiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class ConstructionSiteController {
    private final ConstructionSiteService constructionSiteService;

    public ConstructionSiteController(ConstructionSiteService constructionSiteService) {
        this.constructionSiteService = constructionSiteService;
    }

    @GetMapping
    public List<SiteResponse> getAll() {
        return constructionSiteService.getAll();
    }

    @GetMapping("/{id}")
    public SiteResponse getById(@PathVariable Long id) {
        return constructionSiteService.getById(id);
    }

    @PostMapping
    public ResponseEntity<SiteResponse> create(
            @RequestBody @Valid CreateSiteRequest request
            ) {
        SiteResponse response = constructionSiteService.create(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/manager/{managerId}")
    public List<SiteResponse> getByManager(@PathVariable Long managerId) {
        return constructionSiteService.getByManager(managerId);
    }
}
