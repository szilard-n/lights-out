package org.lightsout.shared.solver;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.lightsout.solution.entity.Solution;
import org.lightsout.solution.entity.SolutionStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@UtilityClass
public class ProblemSolver {

    /* Direction vectors for toggling lights */
    private static final int[] dr = {0, 0, 1, -1, 0};
    private static final int[] dc = {0, 1, 0, 0, -1};

    /**
     * Solves the Lights Out puzzle using a Linear Algebra approach.
     *
     * @param initialGrid The initial state of the grid.
     * @param problemId   The ID of the problem.
     * @return An Optional containing the Solution if solved, otherwise empty.
     */
    public static Optional<Solution> solve(List<List<Byte>> initialGrid, UUID problemId) {
        long startTime = System.currentTimeMillis();

        int gridSize = initialGrid.size();
        int totalLights = gridSize * gridSize;

        int[][] augmentedMatrix = buildAugmentedMatrix(initialGrid, gridSize, totalLights);

        if (!solveGaussianElimination(augmentedMatrix, totalLights)) {
            return Optional.empty();
        }

        List<int[]> moves = extractSolutionMoves(augmentedMatrix, totalLights, gridSize);

        long solvingDurationMs = System.currentTimeMillis() - startTime;
        log.info("Problem with ID {} solved in {} ms with {} moves", problemId, solvingDurationMs, moves.size());
        return Optional.of(createSolution(moves));
    }

    /**
     * Builds the augmented matrix representing the system of linear equations for the Lights Out puzzle.
     *
     * @param initialGrid The initial state of the grid.
     * @param gridSize    The size of the grid.
     * @param totalLights The total number of lights in the grid.
     * @return The augmented matrix.
     */
    private static int[][] buildAugmentedMatrix(List<List<Byte>> initialGrid, int gridSize, int totalLights) {
        int[][] augmentedMatrix = new int[totalLights][totalLights + 1];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int index = row * gridSize + col;
                augmentedMatrix[index][index] = 1; // The cell itself
                for (int i = 0; i < 5; i++) {
                    int newRow = row + dr[i];
                    int newCol = col + dc[i];
                    if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize) {
                        int neighborIndex = newRow * gridSize + newCol;
                        augmentedMatrix[index][neighborIndex] = 1;
                    }
                }
                augmentedMatrix[index][totalLights] = initialGrid.get(row).get(col); // The initial state
            }
        }
        return augmentedMatrix;
    }

    /**
     * Solves the augmented matrix using Gaussian Elimination.
     *
     * @param matrix The augmented matrix.
     * @param size   The size of the matrix.
     * @return True if a solution exists, otherwise false.
     */
    private static boolean solveGaussianElimination(int[][] matrix, int size) {
        forwardElimination(matrix, size);
        return backSubstitution(matrix, size);
    }

    /**
     * Performs forward elimination to convert the matrix into an upper triangular form.
     *
     * @param matrix The augmented matrix.
     * @param size   The size of the matrix.
     */
    private static void forwardElimination(int[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            log.debug("Processing row {} of {}", i + 1, size);
            int pivot = findPivotRow(matrix, size, i);
            swapRows(matrix, i, pivot);
            if (matrix[i][i] == 0) {
                continue;
            }
            eliminateBelow(matrix, size, i);
        }
    }

    /**
     * Finds the pivot row for the current column.
     *
     * @param matrix     The augmented matrix.
     * @param size       The size of the matrix.
     * @param currentRow The current row index.
     * @return The pivot row index.
     */
    private static int findPivotRow(int[][] matrix, int size, int currentRow) {
        int pivot = currentRow;
        for (int j = currentRow + 1; j < size; j++) {
            if (matrix[j][currentRow] > matrix[pivot][currentRow]) {
                pivot = j;
            }
        }
        return pivot;
    }

    /**
     * Swaps two rows in the matrix.
     *
     * @param matrix The augmented matrix.
     * @param row1   The first row index.
     * @param row2   The second row index.
     */
    private static void swapRows(int[][] matrix, int row1, int row2) {
        if (row1 != row2) {
            int[] tempRow = matrix[row1];
            matrix[row1] = matrix[row2];
            matrix[row2] = tempRow;
        }
    }

    /**
     * Eliminates the entries below the pivot row to create an upper triangular matrix.
     *
     * @param matrix     The augmented matrix.
     * @param size       The size of the matrix.
     * @param currentRow The current row index.
     */
    private static void eliminateBelow(int[][] matrix, int size, int currentRow) {
        for (int j = currentRow + 1; j < size; j++) {
            if (matrix[j][currentRow] == 1) {
                for (int k = currentRow; k <= size; k++) {
                    matrix[j][k] ^= matrix[currentRow][k];
                }
            }
        }
    }

    /**
     * Performs back substitution to solve the upper triangular matrix.
     *
     * @param matrix The augmented matrix.
     * @param size   The size of the matrix.
     * @return True if a solution exists, otherwise false.
     */
    private static boolean backSubstitution(int[][] matrix, int size) {
        for (int i = size - 1; i >= 0; i--) {
            if (matrix[i][i] == 0 && matrix[i][size] == 1) {
                return false; // No solution
            }
            for (int j = i + 1; j < size; j++) {
                matrix[i][size] ^= matrix[i][j] & matrix[j][size];
            }
        }
        return true;
    }

    /**
     * Extracts the solution moves from the solved augmented matrix.
     *
     * @param matrix     The augmented matrix.
     * @param totalLights The total number of lights in the grid.
     * @param gridSize   The size of the grid.
     * @return A list of moves needed to solve the puzzle.
     */
    private static List<int[]> extractSolutionMoves(int[][] matrix, int totalLights, int gridSize) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < totalLights; i++) {
            if (matrix[i][totalLights] == 1) {
                moves.add(new int[]{i / gridSize, i % gridSize});
            }
        }
        return moves;
    }

    /**
     * Creates a Solution object from the list of moves.
     *
     * @param moves The list of moves.
     * @return A Solution object.
     */
    private static Solution createSolution(List<int[]> moves) {
        Solution solution = new Solution();
        List<SolutionStep> solutionSteps = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            int[] move = moves.get(i);
            SolutionStep step = SolutionStep.builder()
                    .solution(solution)
                    .index(i)
                    .row(move[0])
                    .col(move[1])
                    .build();
            solutionSteps.add(step);
        }

        solution.setSolutionSteps(solutionSteps);
        return solution;
    }
}