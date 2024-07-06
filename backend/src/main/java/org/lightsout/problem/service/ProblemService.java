package org.lightsout.problem.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.lightsout.problem.dto.CreateProblemRequest;
import org.lightsout.problem.dto.ProblemDto;
import org.lightsout.problem.entity.Problem;
import org.lightsout.problem.mapper.ProblemMapper;
import org.lightsout.problem.repository.ProblemRepository;
import org.lightsout.shared.dto.PaginatedResponse;
import org.lightsout.shared.exception.ResourceNotFoundException;
import org.lightsout.shared.exception.UnsolvableProblemException;
import org.lightsout.shared.solver.ProblemSolver;
import org.lightsout.solution.entity.Solution;

import java.util.List;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class ProblemService {

    @Inject
    ProblemRepository repository;

    @Inject
    ProblemMapper mapper;

    public PaginatedResponse<ProblemDto> getProblems(int pageIndex, int pageSize) {
        PanacheQuery<Problem> query = repository.findAllOrderedByGridSizePaginated(pageIndex, pageSize);

        List<ProblemDto> problemDtos = query.list().stream()
                .map(mapper::toDto).toList();

        return new PaginatedResponse<>(query.count(), problemDtos);
    }

    public ProblemDto getProblem(UUID id) {
        return repository.findByIdOptional(id)
                .map(mapper::toDto)
                .orElseThrow(() -> {
                    String msg = String.format("Problem with ID %s not found", id);
                    return new ResourceNotFoundException(msg);
                });
    }

    @Transactional
    public void createProblem(CreateProblemRequest request) {
        UUID id = UUID.randomUUID();
        log.info("Received new problem. ID: {}", id);

        Solution solution = ProblemSolver.solve(request.grid(), id)
                .orElseThrow(() -> new UnsolvableProblemException(id));

        Problem problem = Problem.builder()
                .id(id)
                .grid(request.grid())
                .size(request.grid().size())
                .solution(solution)
                .build();
        solution.setProblem(problem);
        repository.persist(problem);
    }
}