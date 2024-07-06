package org.lightsout.solution.dto;

import java.util.List;

public record SolutionDto(
        List<SolutionStepDto> solutionSteps
) {
}
