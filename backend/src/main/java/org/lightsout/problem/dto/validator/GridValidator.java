package org.lightsout.problem.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Objects;

public class GridValidator implements ConstraintValidator<Grid, List<List<Byte>>> {

    @Override
    public boolean isValid(List<List<Byte>> grid, ConstraintValidatorContext context) {
        if (Objects.isNull(grid)) {
            return false;
        }

        int gridSize = grid.size();
        if (gridSize < 3 || gridSize > 8) {
            return false;
        }

        for (List<Byte> row : grid) {
            if (row.size() != gridSize) {
                return false;
            }
        }

        return true;
    }
}
