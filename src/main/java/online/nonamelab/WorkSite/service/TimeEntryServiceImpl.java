package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateTimeEntryRequest;
import online.nonamelab.WorkSite.dto.TimeEntryMapper;
import online.nonamelab.WorkSite.dto.TimeEntryResponse;
import online.nonamelab.WorkSite.dto.UpdateTimeEntryRequest;
import online.nonamelab.WorkSite.exception.BusinessException;
import online.nonamelab.WorkSite.exception.site.SiteNotFoundException;
import online.nonamelab.WorkSite.exception.user.UserNotFoundException;
import online.nonamelab.WorkSite.model.ConstructionSite;
import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.model.TimeEntry;
import online.nonamelab.WorkSite.model.User;
import online.nonamelab.WorkSite.repository.ConstructionSiteRepository;
import online.nonamelab.WorkSite.repository.TimeEntryRepository;
import online.nonamelab.WorkSite.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class TimeEntryServiceImpl implements TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final ConstructionSiteRepository siteRepository;
    private final UserRepository userRepository;

    public TimeEntryServiceImpl(TimeEntryRepository repository, ConstructionSiteRepository siteRepository, UserRepository userRepository) {
        this.timeEntryRepository = repository;
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
    }

//    @Override
//    public List<TimeEntryResponse> getAll() {
//        return List.of();
//    }

    @Override
    public List<TimeEntryResponse> getAll(Long userId, int year, int month) {
        User currentUser = getCurrentUser();

        Long targetUserId;

        if (currentUser.getRole() == Role.WORKER) {
            targetUserId = currentUser.getId();
        }
        else if (currentUser.getRole() == Role.MANAGER) {
            targetUserId = (userId != null) ? userId : currentUser.getId();
        }
        else {
            targetUserId = (userId != null) ? userId : currentUser.getId();
        }

        LocalDate start = startOfMonth(year, month);
        LocalDate end = endOfMonth(year, month);

        List<TimeEntry> entries = timeEntryRepository
                .findByUserIdAndDateBetween(targetUserId, start, end);

        return entries.stream()
                .map(TimeEntryMapper::toResponse)
                .toList();
    }

    @Override
    public TimeEntryResponse getById(Long id) {
        return null;
    }

    @Override
    public TimeEntryResponse create(CreateTimeEntryRequest request) {
        User user = getCurrentUser();

        if (user.getRole() == Role.ADMIN) {
            throw new BusinessException("ADMIN cannot create time entries");
        }

        ConstructionSite site = siteRepository.findById(request.siteId())
                .orElseThrow(() -> new SiteNotFoundException(request.siteId()));

        if (request.hours() <= 0 || request.hours() > 24) {
            throw new BusinessException("Invalid hours");
        }

        double existingHours = timeEntryRepository
                .findByUserIdAndDate(user.getId(), request.date())
                .stream()
                .mapToDouble(TimeEntry::getHours)
                .sum();

        double totalHours = existingHours + request.hours();

        if (totalHours > 24) {
            throw new BusinessException("Total hours per day cannot exceed 24");
        }

        TimeEntry entry = TimeEntryMapper.toTimeEntry(request);
//        entry.setDate(request.date());
//        entry.setHours(request.hours());

        entry.setUser(user);
        entry.setSite(site);

        return TimeEntryMapper.toResponse(timeEntryRepository.save(entry));
    }

    @Override
    public TimeEntryResponse update(Long id, UpdateTimeEntryRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private LocalDate startOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    private LocalDate endOfMonth(int year, int month) {
        return LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth());
    }
}
