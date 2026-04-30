package online.nonamelab.WorkSite.repository;

import online.nonamelab.WorkSite.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    List<TimeEntry> findByUserId(Long userId);

    List<TimeEntry> findBySiteId(Long siteId);

    List<TimeEntry> findByDateBetween(LocalDate start, LocalDate end);

    List<TimeEntry> findByUserIdAndDate(Long userId, LocalDate date);

    List<TimeEntry> findByUserIdAndDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end
    );
}
