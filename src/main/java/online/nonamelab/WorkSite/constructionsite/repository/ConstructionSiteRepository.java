package online.nonamelab.WorkSite.constructionsite.repository;

import online.nonamelab.WorkSite.model.ConstructionSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstructionSiteRepository extends JpaRepository<ConstructionSite, Long> {
    List<ConstructionSite> findByManagerId(Long managerId);
}
