package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.dto.SiteResponse;
import online.nonamelab.WorkSite.dto.UpdateSiteRequest;
import online.nonamelab.WorkSite.dto.UserResponse;
import online.nonamelab.WorkSite.model.Priority;
import online.nonamelab.WorkSite.model.SiteStatus;

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
