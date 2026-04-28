package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateSiteRequest;
import online.nonamelab.WorkSite.dto.SiteMapper;
import online.nonamelab.WorkSite.dto.SiteResponse;
import online.nonamelab.WorkSite.dto.UpdateSiteRequest;
import online.nonamelab.WorkSite.exception.SiteNotFoundException;
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
            throw new RuntimeException("Only MANAGER or ADMIN can be assigned as manager");
        }

        ConstructionSite site = SiteMapper.toSite(request, manager);

        return SiteMapper.toResponse(constructionSiteRepository.save(site));
    }

    @Override
    public SiteResponse update(Long id, UpdateSiteRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

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
