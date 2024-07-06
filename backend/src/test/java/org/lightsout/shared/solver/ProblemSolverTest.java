package org.lightsout.shared.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.lightsout.shared.util.LightsOutRunner;
import org.lightsout.shared.util.TestUtils;
import org.lightsout.shared.util.TestUtils.SolvableGrids;
import org.lightsout.shared.util.TestUtils.UnsolvableGrids;
import org.lightsout.solution.entity.Solution;
import org.lightsout.solution.entity.SolutionStep;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.lightsout.shared.util.TestUtils.toList;


class ProblemSolverTest {

    @ParameterizedTest
    @MethodSource("solvableGrids")
    @DisplayName("Should correctly solve the given Lights Out problem")
    void solveSolvableGrids(List<List<Byte>> problemGrid) {
        Optional<Solution> solutionOptional = ProblemSolver.solve(problemGrid, UUID.randomUUID());
        Assertions.assertTrue(solutionOptional.isPresent(), "The solution should be present");

        List<SolutionStep> solutionSteps = solutionOptional.get().getSolutionSteps();
        boolean isSolved = LightsOutRunner.init(problemGrid, solutionSteps).run();
        Assertions.assertTrue(isSolved, "All lights should be turned off");
    }

    @Test
    @DisplayName("Should not be able to find solution for the given Lights Out problem")
    void solveUnsolvableGrids() {
        Optional<Solution> solutionOptional = ProblemSolver.solve(toList(UnsolvableGrids.GRID_4_X_4), UUID.randomUUID());
        Assertions.assertTrue(solutionOptional.isEmpty(), "The solution should not be present");
    }

    private static Stream<Arguments> solvableGrids() {
        return Stream.of(
                Arguments.of(toList(SolvableGrids.GRID_3_X_3)),
                Arguments.of(toList(SolvableGrids.GRID_4_X_4)),
                Arguments.of(toList(SolvableGrids.GRID_5_X_5)),
                Arguments.of(toList(SolvableGrids.GRID_6_X_6)),
                Arguments.of(toList(SolvableGrids.GRID_7_X_7)),
                Arguments.of(toList(SolvableGrids.GRID_8_X_8))
        );
    }
}
