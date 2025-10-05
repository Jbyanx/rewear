package com.devops.backend.rewear.specification;

import com.devops.backend.rewear.dtos.request.WearFilter;
import com.devops.backend.rewear.entities.Wear;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class WearSpecification {

    public static Specification<Wear> byFilter(WearFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Siempre filtramos por status = AVAILABLE
            predicates.add(cb.equal(root.get("status"), "AVAILABLE"));

            if (filter.name() != null && !filter.name().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
            }
            if (filter.category() != null) {
                predicates.add(cb.equal(root.get("category"), filter.category()));
            }
            if (filter.genre() != null) {
                predicates.add(cb.equal(root.get("genre"), filter.genre()));
            }
            if (filter.color() != null && !filter.color().isEmpty()) {
                predicates.add(cb.equal(root.get("color"), filter.color()));
            }
            if (filter.size() != null) {
                predicates.add(cb.equal(root.get("size"), filter.size()));
            }
            if (filter.condition() != null) {
                predicates.add(cb.equal(root.get("condition"), filter.condition()));
            }
            if (filter.brand() != null && !filter.brand().isEmpty()) {
                predicates.add(cb.equal(root.get("brand"), filter.brand()));
            }
            if (filter.material() != null && !filter.material().isEmpty()) {
                predicates.add(cb.equal(root.get("material"), filter.material()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
