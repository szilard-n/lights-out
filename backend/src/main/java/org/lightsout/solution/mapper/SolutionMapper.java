package org.lightsout.solution.mapper;

import org.lightsout.solution.dto.SolutionDto;
import org.lightsout.solution.entity.Solution;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "jakarta",
        uses = {SolutionStepMapper.class}
)
public interface SolutionMapper {

    SolutionDto toDto(Solution solution);
}
