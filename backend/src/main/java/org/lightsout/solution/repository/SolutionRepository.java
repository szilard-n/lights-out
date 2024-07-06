package org.lightsout.solution.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.lightsout.solution.entity.Solution;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class SolutionRepository implements PanacheRepositoryBase<Solution, UUID> {

    public PanacheQuery<Solution> findAllPaginated(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize);
    }

    public Optional<Solution> findByProblemIdOptional(UUID problemId) {
        return find("problem.id", problemId).singleResultOptional();
    }
}
