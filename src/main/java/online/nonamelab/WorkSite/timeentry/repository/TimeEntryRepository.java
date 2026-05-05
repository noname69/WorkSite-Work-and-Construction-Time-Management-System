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
    Optional<TimeEntry> findByUser_IdAndDate(Long userId, LocalDate date);

//    List<TimeEntry> findByUser_Id(Long userId);
//
//    List<TimeEntry> findByUser_IdAndDateBetween(
//            Long userId,
//            LocalDate start,
//            LocalDate end
//    );

//    List<TimeEntry> findByDate(LocalDate date);
//
//    List<TimeEntry> findAllByUser_IdOrderByDateDesc(Long userId);

    List<TimeEntry> findAllByUser_IdAndDateBetweenOrderByDateDesc(
            Long userId,
            LocalDate start,
            LocalDate end
    );

}
