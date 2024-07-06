package org.lightsout.shared.util;

import org.lightsout.solution.entity.SolutionStep;

import java.util.ArrayList;
import java.util.List;


public class LightsOutRunner {

    private static final int[] dr = {0, 0, 1, -1, 0};
    private static final int[] dc = {0, 1, 0, 0, -1};

    private final List<List<Byte>> grid;
    private final List<SolutionStep> solutionSteps;

    private LightsOutRunner(List<List<Byte>> grid, List<SolutionStep> solutionSteps) {
        this.grid = grid;
        this.solutionSteps = solutionSteps;
    }

    public static LightsOutRunner init(List<List<Byte>> grid, List<SolutionStep> solutionSteps) {
        return new LightsOutRunner(deepCopyGrid(grid), solutionSteps);
    }

    public boolean run() {
        for (SolutionStep step : solutionSteps) {
            toggleLight(grid, step.getRow(), step.getCol());
        }
        return isSolved(grid);
    }

    private void toggleLight(List<List<Byte>> grid, int row, int col) {
        for (int i = 0; i < 5; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            if (newRow >= 0 && newRow < grid.size() && newCol >= 0 && newCol < grid.get(0).size()) {
                grid.get(newRow).set(newCol, (byte) (grid.get(newRow).get(newCol) ^ 1));
            }
        }
    }

    private boolean isSolved(List<List<Byte>> grid) {
        for (List<Byte> row : grid) {
            for (Byte cell : row) {
                if (cell != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<List<Byte>> deepCopyGrid(List<List<Byte>> original) {
        List<List<Byte>> copy = new ArrayList<>();
        for (List<Byte> row : original) {
            copy.add(new ArrayList<>(row));
        }
        return copy;
    }
}
