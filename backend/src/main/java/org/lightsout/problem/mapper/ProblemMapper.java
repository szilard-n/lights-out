package org.lightsout.problem.mapper;

import org.lightsout.problem.dto.ProblemDto;
import org.lightsout.problem.entity.Problem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface ProblemMapper {

    ProblemDto toDto(Problem problem);
}
