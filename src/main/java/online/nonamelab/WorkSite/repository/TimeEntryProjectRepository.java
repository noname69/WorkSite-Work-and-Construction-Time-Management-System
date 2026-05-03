package online.nonamelab.WorkSite.repository;

import online.nonamelab.WorkSite.model.TimeEntryProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeEntryProjectRepository extends JpaRepository<TimeEntryProject, Long> {
    // all projects inside a TimeEntry
    List<TimeEntryProject> findByTimeEntry_Id(Long timeEntryId);

    // check if entry already has project (YOUR CURRENT RULE)
    boolean existsByTimeEntry_Id(Long timeEntryId);

    // get by site (for reporting)
    List<TimeEntryProject> findByConstructionSite_Id(Long siteId);

    // optional: user + site reports
    List<TimeEntryProject> findByTimeEntry_User_IdAndConstructionSite_Id(
            Long userId,
            Long siteId
    );
}
