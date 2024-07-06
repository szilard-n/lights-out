package org.lightsout.solution.mapper;

import org.lightsout.solution.dto.SolutionStepDto;
import org.lightsout.solution.entity.SolutionStep;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface SolutionStepMapper {

    SolutionStepDto toDto(SolutionStep solutionStep);
}
