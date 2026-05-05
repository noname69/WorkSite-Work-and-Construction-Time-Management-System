package online.nonamelab.WorkSite.user.repository;

import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Page<User> findByRoleNot(Role role, Pageable pageable);

    List<User> findAllByDeletedFalse();
}
