export interface LightsOutSolution {
  solutionSteps: SolutionStep[];
}

export interface SolutionStep {
  index: number;
  row: number;
  col: number;
}
