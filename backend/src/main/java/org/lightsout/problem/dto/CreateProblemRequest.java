package org.lightsout.problem.dto;

import org.lightsout.problem.dto.validator.Grid;

import java.util.List;

public record CreateProblemRequest(
        @Grid
        List<List<Byte>> grid
) {
}
