package online.nonamelab.WorkSite.timeentry.repository;

import online.nonamelab.WorkSite.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
//    List<TimeEntry> findByUserId(Long userId);
//
//    List<TimeEntry> findBySiteId(Long siteId);
//
//    List<TimeEntry> findByDateBetween(LocalDate start, LocalDate end);
//
//    List<TimeEntry> findByUserIdAndDate(Long userId, LocalDate date);
//
//    List<TimeEntry> findByUserIdAndDateBetween(
//            Long userId,
//            LocalDate start,
//            LocalDate end
//    );
    // find entry for specific user and day (VERY IMPORTANT)
    Optional<TimeEntry> findByUser_IdAndDate(Long userId, LocalDate date);

    // all entries of a user
    List<TimeEntry> findByUser_Id(Long userId);

    // entries in date range (useful for reports)
    List<TimeEntry> findByUser_IdAndDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end
    );

    // manager/admin: all entries for a day
    List<TimeEntry> findByDate(LocalDate date);

    List<TimeEntry> findAllByUser_IdOrderByDateDesc(Long userId);

    List<TimeEntry> findAllByUser_IdAndDateBetweenOrderByDateDesc(
            Long userId,
            LocalDate start,
            LocalDate end
    );

}
