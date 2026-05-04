package online.nonamelab.WorkSite.constructionsite.controller;

import jakarta.validation.Valid;
import online.nonamelab.WorkSite.constructionsite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.constructionsite.dto.SiteResponse;
import online.nonamelab.WorkSite.constructionsite.dto.UpdateSiteRequest;
import online.nonamelab.WorkSite.constructionsite.service.ConstructionSiteService;
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

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @GetMapping
    public List<SiteResponse> getAll() {
        return constructionSiteService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @GetMapping("/{id}")
    public SiteResponse getById(@PathVariable Long id) {
        return constructionSiteService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<SiteResponse> create(
            @RequestBody @Valid CreateSiteRequest request
            ) {
        SiteResponse response = constructionSiteService.create(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PatchMapping("/{id}")
    public ResponseEntity<SiteResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateSiteRequest request
            ) {

        SiteResponse response = constructionSiteService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        constructionSiteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/manager/{managerId}")
    public List<SiteResponse> getByManager(@PathVariable Long managerId) {
        return constructionSiteService.getByManager(managerId);
    }
}
