package online.nonamelab.WorkSite.constructionsite.service.impl;

import online.nonamelab.WorkSite.constructionsite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.constructionsite.dto.SiteResponse;
import online.nonamelab.WorkSite.constructionsite.dto.UpdateSiteRequest;
import online.nonamelab.WorkSite.constructionsite.repository.ConstructionSiteRepository;
import online.nonamelab.WorkSite.constructionsite.service.ConstructionSiteService;
import online.nonamelab.WorkSite.exception.site.SiteNotFoundException;
import online.nonamelab.WorkSite.exception.user.InvalidRoleException;
import online.nonamelab.WorkSite.exception.user.ManagerNotFoundException;
import online.nonamelab.WorkSite.constructionsite.mapper.SiteMapper;
import online.nonamelab.WorkSite.constructionsite.model.ConstructionSite;
import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.user.model.User;
import online.nonamelab.WorkSite.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstructionSiteServiceImpl implements ConstructionSiteService {

    private final ConstructionSiteRepository constructionSiteRepository;
    private final UserRepository userRepository;

    public ConstructionSiteServiceImpl(ConstructionSiteRepository constructionSiteRepository, UserRepository userRepository) {
        this.constructionSiteRepository = constructionSiteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<SiteResponse> getAll() {
        return SiteMapper.toResponseList(constructionSiteRepository.findAll());
    }

    @Override
    public SiteResponse getById(Long id) {
        ConstructionSite site = constructionSiteRepository.findById(id)
                .orElseThrow(() -> new SiteNotFoundException(id));

        return SiteMapper.toResponse(site);
    }

    @Override
    public SiteResponse create(CreateSiteRequest request) {
        User manager = userRepository.findById(request.managerId())
                .orElseThrow(() -> new ManagerNotFoundException(request.managerId()));

        if(manager.getRole() != Role.MANAGER && manager.getRole() != Role.ADMIN) {
            throw new InvalidRoleException("Only MANAGER or ADMIN can be assigned as manager");
        }

        ConstructionSite site = SiteMapper.toSite(request, manager);

        return SiteMapper.toResponse(constructionSiteRepository.save(site));
    }

    @Override
    public SiteResponse update(Long id, UpdateSiteRequest request) {
        ConstructionSite site = constructionSiteRepository.findById(id)
                .orElseThrow(() -> new SiteNotFoundException(id));

        if (request.managerId() != null) {

            User manager = userRepository.findById(request.managerId())
                    .orElseThrow(() -> new ManagerNotFoundException(request.managerId()));

            if (manager.getRole() != Role.MANAGER) {
                throw new InvalidRoleException(
                        "User with id " + request.managerId() + " is not a MANAGER"
                );
            }

            site.setManager(manager);
        }

        if (request.name() != null) {
            site.setName(request.name());
        }

        if (request.description() != null) {
            site.setDescription(request.description());
        }

        if (request.status() != null) {
            site.setStatus(request.status());
        }

        if (request.priority() != null) {
            site.setPriority(request.priority());
        }

        if (request.startDate() != null) {
            site.setStartDate(request.startDate());
        }

        if (request.endDate() != null) {
            site.setEndDate(request.endDate());
        }

        ConstructionSite saved = constructionSiteRepository.save(site);

        return SiteMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        ConstructionSite site = constructionSiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Site not found: " + id));

        constructionSiteRepository.delete(site);
    }

    @Override
    public List<SiteResponse> getByManager(Long managerId) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        return constructionSiteRepository.findByManagerId(managerId)
                .stream()
                .map(SiteMapper::toResponse)
                .toList();
    }
}
