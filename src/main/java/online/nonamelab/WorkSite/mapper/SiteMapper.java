package online.nonamelab.WorkSite.mapper;

import online.nonamelab.WorkSite.constructionsite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.constructionsite.dto.SiteResponse;
import online.nonamelab.WorkSite.model.ConstructionSite;
import online.nonamelab.WorkSite.model.User;

import java.util.List;

public class SiteMapper {
    public static ConstructionSite toSite(CreateSiteRequest request, User manager) {
        if (request == null) return null;

        ConstructionSite site = new ConstructionSite();

        site.setName(request.name());
        site.setDescription(request.description());
        site.setStatus(request.status());
        site.setPriority(request.priority());
        site.setStartDate(request.startDate());
        site.setEndDate(request.endDate());
        site.setManager(manager);

        return site;
    }

    public static SiteResponse toResponse(ConstructionSite site) {
        return new SiteResponse(
                site.getId(),
                site.getName(),
                site.getDescription(),
                site.getStatus(),
                site.getPriority(),
                site.getStartDate(),
                site.getEndDate(),
                site.getManager() != null ? site.getManager().getId() : null
        );
    }

    public static List<SiteResponse> toResponseList(List<ConstructionSite> sites) {
        return sites.stream()
                .map(SiteMapper::toResponse)
                .toList();
    }
}
