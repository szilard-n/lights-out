package org.lightsout.solution.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lightsout.shared.dto.PaginatedResponse;
import org.lightsout.shared.exception.ResourceNotFoundException;
import org.lightsout.solution.dto.SolutionDto;
import org.lightsout.solution.entity.Solution;
import org.lightsout.solution.mapper.SolutionMapper;
import org.lightsout.solution.repository.SolutionRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SolutionService {

    @Inject
    SolutionRepository repository;

    @Inject
    SolutionMapper mapper;

    public PaginatedResponse<SolutionDto> getSolutions(int pageIndex, int pageSize) {
        PanacheQuery<Solution> query = repository.findAllPaginated(pageIndex, pageSize);

        List<SolutionDto> solutionDtos = query.list().stream()
                .map(mapper::toDto).toList();

        return new PaginatedResponse<>(query.count(), solutionDtos);
    }

    public SolutionDto getSolution(UUID problemId) {
        return repository.findByProblemIdOptional(problemId)
                .map(mapper::toDto)
                .orElseThrow(() -> {
                    String msg = String.format("Solution for problem with ID %s not found", problemId);
                    return new ResourceNotFoundException(msg);
                });
    }
}
