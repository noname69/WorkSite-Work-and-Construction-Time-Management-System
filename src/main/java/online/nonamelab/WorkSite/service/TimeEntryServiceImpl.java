package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateTimeEntryProjectRequest;
import online.nonamelab.WorkSite.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.dto.TimeEntryMapper;
import online.nonamelab.WorkSite.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.exception.BusinessException;
import online.nonamelab.WorkSite.exception.site.SiteNotFoundException;
import online.nonamelab.WorkSite.model.ConstructionSite;
import online.nonamelab.WorkSite.model.TimeEntry;
import online.nonamelab.WorkSite.model.TimeEntryProject;
import online.nonamelab.WorkSite.model.TimeEntryStatus;
import online.nonamelab.WorkSite.repository.ConstructionSiteRepository;
import online.nonamelab.WorkSite.repository.TimeEntryProjectRepository;
import online.nonamelab.WorkSite.repository.TimeEntryRepository;
import online.nonamelab.WorkSite.repository.UserRepository;
import online.nonamelab.WorkSite.security.SecurityUtils;
import online.nonamelab.WorkSite.security.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimeEntryServiceImpl implements TimeEntryService {
    private final UserRepository userRepository;
    private final ConstructionSiteRepository constructionSiteRepository;
    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryProjectRepository timeEntryProjectRepository;
    private final PasswordEncoder encoder;
    private final SecurityUtils securityUtils;


    public TimeEntryServiceImpl(UserRepository userRepository, ConstructionSiteRepository constructionSiteRepository,
                                TimeEntryRepository timeEntryRepository, TimeEntryProjectRepository timeEntryProjectRepository,
                                PasswordEncoder encoder,
                                SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.constructionSiteRepository = constructionSiteRepository;
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryProjectRepository = timeEntryProjectRepository;
        this.encoder = encoder;
        this.securityUtils = securityUtils;
    }


    @Override
    public TimeEntryResponse createOrGet(CreateTimeEntryRequest request) {
        UserPrincipal current = securityUtils.getCurrentUser();

        Optional<TimeEntry> existingEntry =
                timeEntryRepository.findByUser_IdAndDate(current.getId(), request.date());

        TimeEntry entry;

        if (existingEntry.isPresent()) {
            entry = existingEntry.get();

            // ❗ check if already has project (your rule)
            boolean hasProject =
                    timeEntryProjectRepository.existsByTimeEntry_Id(entry.getId());

            if (hasProject) {
                throw new BusinessException("Time entry already has a project for this day");
            }

        } else {
            // 2. create new TimeEntry
            entry = new TimeEntry();
            entry.setDate(request.date());
            entry.setStatus(
                    request.status() != null ? request.status() : TimeEntryStatus.WORKING
            );
            entry.setUser(userRepository.getReferenceById(current.getId()));
            entry.setCreatedAt(LocalDateTime.now());
            entry.setLocked(false);

            entry = timeEntryRepository.save(entry);
        }

        // 3. create project
        CreateTimeEntryProjectRequest projectRequest = request.project();

        ConstructionSite site = constructionSiteRepository.findById(
                projectRequest.constructionSiteId()
        ).orElseThrow(() -> new SiteNotFoundException(projectRequest.constructionSiteId()));

        TimeEntryProject project = new TimeEntryProject();
        project.setTimeEntry(entry);
        project.setConstructionSite(site);
        project.setHours(projectRequest.hours());
        project.setDistance(projectRequest.distance());

        timeEntryProjectRepository.save(project);

        // 4. attach project to entry (optional but clean)
        entry.getProjects().add(project);

        // 5. return full response
        return TimeEntryMapper.toResponse(entry);
    }

    @Override
    public List<TimeEntryResponse> getMyEntries() {
        return List.of();
    }

    @Override
    public TimeEntryResponse getByDate(LocalDate date) {
        return null;
    }

    @Override
    public List<TimeEntryResponse> getAll() {
        return List.of();
    }
}
