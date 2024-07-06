package org.lightsout.problem.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lightsout.problem.entity.Problem;

import java.util.UUID;

@ApplicationScoped
public class ProblemRepository implements PanacheRepositoryBase<Problem, UUID> {

    public PanacheQuery<Problem> findAllOrderedByGridSizePaginated(int pageIndex, int pageSize) {
        return findAll(Sort.by("size", Sort.Direction.Ascending))
                .page(pageIndex, pageSize);
    }

}
