package online.nonamelab.WorkSite.constructionsite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import online.nonamelab.WorkSite.model.Priority;
import online.nonamelab.WorkSite.model.SiteStatus;

import java.time.LocalDate;

public record UpdateSiteRequest(
        String name,

        String description,

        SiteStatus status,

        Priority priority,

        LocalDate startDate,
        LocalDate endDate,
        Long managerId

) {}
