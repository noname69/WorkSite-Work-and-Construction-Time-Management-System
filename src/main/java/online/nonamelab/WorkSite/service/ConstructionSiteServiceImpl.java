package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.dto.SiteMapper;
import online.nonamelab.WorkSite.dto.SiteResponse;
import online.nonamelab.WorkSite.dto.UpdateSiteRequest;
import online.nonamelab.WorkSite.exception.user.InvalidRoleException;
import online.nonamelab.WorkSite.exception.SiteNotFoundException;
import online.nonamelab.WorkSite.exception.user.UserNotFoundException;
import online.nonamelab.WorkSite.model.ConstructionSite;
import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.model.User;
import online.nonamelab.WorkSite.repository.ConstructionSiteRepository;
import online.nonamelab.WorkSite.repository.UserRepository;
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
                .orElseThrow(() -> new RuntimeException("Manager not found"));

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

        User manager = null;

        if(request.managerId() != null) {
            manager = userRepository.findById(request.managerId())
                    .orElseThrow(() -> new UserNotFoundException(request.managerId()));
        }

        if (manager != null && manager.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only MANAGER can be assigned as manager");
        }

        site.setName(request.name());
        site.setDescription(request.description());
        site.setStatus(request.status());
        site.setPriority(request.priority());
        site.setStartDate(request.startDate());
        site.setEndDate(request.endDate());
        site.setManager(manager);

        return SiteMapper.toResponse(constructionSiteRepository.save(site));
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
