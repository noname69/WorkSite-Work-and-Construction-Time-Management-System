package online.nonamelab.WorkSite.timeentry.repository;

import online.nonamelab.WorkSite.timeentry.model.TimeEntrySite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeEntrySiteRepository extends JpaRepository<TimeEntrySite, Long> {
    List<TimeEntrySite> findByTimeEntry_Id(Long timeEntryId);

    boolean existsByTimeEntry_Id(Long timeEntryId);

    List<TimeEntrySite> findByConstructionSite_Id(Long siteId);

    List<TimeEntrySite> findByTimeEntry_User_IdAndConstructionSite_Id(
            Long userId,
            Long siteId
    );
}
