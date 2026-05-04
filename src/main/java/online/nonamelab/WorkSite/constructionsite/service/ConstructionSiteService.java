package online.nonamelab.WorkSite.constructionsite.service;

import online.nonamelab.WorkSite.constructionsite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.constructionsite.dto.SiteResponse;
import online.nonamelab.WorkSite.constructionsite.dto.UpdateSiteRequest;

import java.util.List;

public interface ConstructionSiteService {

    List<SiteResponse> getAll();

    SiteResponse getById(Long id);

    SiteResponse create(CreateSiteRequest request);

    SiteResponse update(Long id, UpdateSiteRequest request);

    void delete(Long id);

//    void assignWorker(Long siteId, Long userId);
//
//    void removeWorker(Long siteId, Long userId);
//
//    List<UserResponse> getWorkers(Long siteId);
//
//    SiteResponse changeManager(Long siteId, Long managerId);
//
    List<SiteResponse> getByManager(Long managerId);
//
//    List<SiteResponse> getByStatus(SiteStatus status);
//
//    List<SiteResponse> getByPriority(Priority priority);
//
//    SiteResponse changeStatus(Long siteId, SiteStatus status);
}
