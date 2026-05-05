package online.nonamelab.WorkSite.timeentry.service.impl;

import jakarta.transaction.Transactional;
import online.nonamelab.WorkSite.constructionsite.repository.ConstructionSiteRepository;
import online.nonamelab.WorkSite.exception.BusinessException;
import online.nonamelab.WorkSite.exception.site.SiteNotFoundException;
import online.nonamelab.WorkSite.mapper.TimeEntryMapper;
import online.nonamelab.WorkSite.model.*;
import online.nonamelab.WorkSite.security.SecurityUtils;
import online.nonamelab.WorkSite.security.UserPrincipal;
import online.nonamelab.WorkSite.timeentry.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.timeentry.dto.CreateTimeEntrySiteRequest;
import online.nonamelab.WorkSite.timeentry.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.timeentry.repository.TimeEntryRepository;
import online.nonamelab.WorkSite.timeentry.repository.TimeEntrySiteRepository;
import online.nonamelab.WorkSite.timeentry.service.TimeEntryService;
import online.nonamelab.WorkSite.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeEntryServiceImpl implements TimeEntryService {
    private final UserRepository userRepository;
    private final ConstructionSiteRepository constructionSiteRepository;
    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntrySiteRepository timeEntrySiteRepository;
    private final SecurityUtils securityUtils;


    public TimeEntryServiceImpl(UserRepository userRepository,
                                ConstructionSiteRepository constructionSiteRepository,
                                TimeEntryRepository timeEntryRepository,
                                TimeEntrySiteRepository timeEntrySiteRepository,
                                SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.constructionSiteRepository = constructionSiteRepository;
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntrySiteRepository = timeEntrySiteRepository;
        this.securityUtils = securityUtils;
    }

    @Transactional
    @Override
    public TimeEntryResponse createOrGet(CreateTimeEntryRequest request) {
        // get current user
        UserPrincipal current = securityUtils.getCurrentUser();

        // date validation, no future
        if (request.date().isAfter(LocalDate.now())) {
            throw new BusinessException("Cannot create time entry in the future");
        }

        // find or create entry
        TimeEntry entry = timeEntryRepository
                .findByUser_IdAndDate(current.getId(), request.date())
                .orElseGet(() -> {
                    TimeEntry newEntry = new TimeEntry();
                    newEntry.setDate(request.date());
                    newEntry.setUser(userRepository.getReferenceById(current.getId()));
                    newEntry.setCreatedAt(LocalDateTime.now());
                    newEntry.setLocked(false);

                    newEntry.setStatus(
                            request.status() != null
                                    ? request.status()
                                    : TimeEntryStatus.WORKING
                    );

                    return timeEntryRepository.save(newEntry);
                });

        // lock check
        if (entry.isLocked()) {
            throw new BusinessException("Time entry is locked");
        }

        // status update
        if (request.status() != null) {
            entry.setStatus(request.status());
        }

        TimeEntryStatus status = entry.getStatus();
        CreateTimeEntrySiteRequest siteRequest = request.site();

        // not working days
        if (status != TimeEntryStatus.WORKING) {

            if (siteRequest != null) {
                throw new BusinessException(
                        "Site is not allowed when status is " + status
                );
            }

            entry.getSites().clear();

            return TimeEntryMapper.toResponse(entry);
        }

        if (siteRequest == null) {
            throw new BusinessException("Site is required when status is WORKING");
        }

        boolean hasSite = timeEntrySiteRepository.existsByTimeEntry_Id(entry.getId());

        if (hasSite) {
            throw new BusinessException("Only one site per day is allowed");
        }

        if (siteRequest.hours() <= 0 || siteRequest.hours() > 24) {
            throw new BusinessException("Hours must be between 1 and 24");
        }

        if (siteRequest.distance() < 0) {
            throw new BusinessException("Distance cannot be negative");
        }

        // site
        ConstructionSite site = constructionSiteRepository.findById(
                siteRequest.constructionSiteId()
        ).orElseThrow(() ->
                new SiteNotFoundException(siteRequest.constructionSiteId())
        );

        if (site.getStatus() == SiteStatus.FINISHED) {
            throw new BusinessException("Cannot log time to finished site");
        }

        TimeEntrySite entrySite = new TimeEntrySite();
        entrySite.setTimeEntry(entry);
        entrySite.setConstructionSite(site);
        entrySite.setHours(siteRequest.hours());
        entrySite.setDistance(siteRequest.distance());

        timeEntrySiteRepository.save(entrySite);

        entry.getSites().add(entrySite);

        return TimeEntryMapper.toResponse(entry);
    }

    @Transactional
    @Override
    public List<TimeEntryResponse> getMyEntries(LocalDate from, LocalDate to) {

        UserPrincipal current = securityUtils.getCurrentUser();

        LocalDate start = from != null
                ? from
                : LocalDate.of(2000, 1, 1);

        LocalDate end = to != null
                ? to
                : LocalDate.now();

        List<TimeEntry> entries = timeEntryRepository
                .findAllByUser_IdAndDateBetweenOrderByDateDesc(
                        current.getId(),
                        start,
                        end
                );

        return entries.stream()
                .map(TimeEntryMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public List<TimeEntryResponse> getByUserAndMonth(Long userId, Integer year, Integer month) {

        LocalDate now = LocalDate.now();

        int y = (year != null) ? year : now.getYear();
        int m = (month != null) ? month : now.getMonthValue();

        LocalDate start = LocalDate.of(y, m, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<TimeEntry> entries = timeEntryRepository
                .findAllByUser_IdAndDateBetweenOrderByDateDesc(
                        userId,
                        start,
                        end
                );

        return entries.stream()
                .map(TimeEntryMapper::toResponse)
                .toList();
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
