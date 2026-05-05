package online.nonamelab.WorkSite.user.specifications;

import jakarta.persistence.criteria.Predicate;
import online.nonamelab.WorkSite.user.dto.UserFilter;
import online.nonamelab.WorkSite.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filter(UserFilter filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // deleted filter
            if (filter.getDeleted() != null) {
                predicates.add(cb.equal(root.get("deleted"), filter.getDeleted()));
            }

            // role filter
            if (filter.getRole() != null) {
                predicates.add(cb.equal(root.get("role"), filter.getRole()));
            }

            // search (name/email)
            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                String like = "%" + filter.getSearch().toLowerCase() + "%";

                Predicate nameMatch = cb.like(cb.lower(root.get("name")), like);
                Predicate emailMatch = cb.like(cb.lower(root.get("email")), like);

                predicates.add(cb.or(nameMatch, emailMatch));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
