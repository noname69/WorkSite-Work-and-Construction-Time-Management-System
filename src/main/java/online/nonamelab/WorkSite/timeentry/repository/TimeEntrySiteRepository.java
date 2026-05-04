package online.nonamelab.WorkSite.timeentry.repository;

import online.nonamelab.WorkSite.model.TimeEntrySite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeEntrySiteRepository extends JpaRepository<TimeEntrySite, Long> {
    // all projects inside a TimeEntry
    List<TimeEntrySite> findByTimeEntry_Id(Long timeEntryId);

    // check if entry already has project (YOUR CURRENT RULE)
    boolean existsByTimeEntry_Id(Long timeEntryId);

    // get by site (for reporting)
    List<TimeEntrySite> findByConstructionSite_Id(Long siteId);

    // optional: user + site reports
    List<TimeEntrySite> findByTimeEntry_User_IdAndConstructionSite_Id(
            Long userId,
            Long siteId
    );
}
