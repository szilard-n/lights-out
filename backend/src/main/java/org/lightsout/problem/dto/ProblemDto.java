package org.lightsout.problem.dto;

import java.util.List;
import java.util.UUID;

public record ProblemDto(
        UUID id,
        List<List<Byte>> grid
) {
}
