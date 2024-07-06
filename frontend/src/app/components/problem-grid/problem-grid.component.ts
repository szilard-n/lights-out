import { ChangeDetectionStrategy, Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { LightsOutProblem } from "../../shared/model/lights-out-problem.model";
import { NgForOf, NgIf } from "@angular/common";
import { SolutionsClient } from "../../core/client/solutions.client";
import { LightsOutSolution } from "../../shared/model/lights-out-solution.model";

@Component({
  selector: 'app-problem-grid',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './problem-grid.component.html',
  styleUrls: ['./problem-grid.component.css'],
})
export class ProblemGridComponent implements OnChanges {
  @Input() problem!: LightsOutProblem;
  @Input() isNew!: boolean;
  @Input() isEditable!: boolean;
  @Input() showSolution: boolean = false;

  solution: LightsOutSolution | null = null;

  constructor(private solutionsClient: SolutionsClient) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['showSolution'] || changes['problem']) {
      if (this.showSolution && this.problem && !this.solution) {
        this.loadSolution();
      } else {
        this.solution = null;
      }
    }
  }

  isSolutionCell(rowIndex: number, colIndex: number): boolean {
    return this.solution?.solutionSteps
      .some(step => step.row === rowIndex && step.col === colIndex) ?? false;
  }

  toggleCellLights(row: number, column: number): void {
    if (this.isEditable) {
      this.toggle(row, column);
      if (!this.isNew) {
        this.toggleIfInBounds(row - 1, column);
        this.toggleIfInBounds(row + 1, column);
        this.toggleIfInBounds(row, column - 1);
        this.toggleIfInBounds(row, column + 1);
      }
    }
  }

  private toggleIfInBounds(row: number, column: number): void {
    const highestIndex = this.problem.grid[0].length - 1;
    const isInBounds = (value: number): boolean => 0 <= value && value <= highestIndex;

    if (isInBounds(row) && isInBounds(column)) {
      this.toggle(row, column);
    }
  }

  private toggle(row: number, column: number): void {
    this.problem.grid[row][column] = this.problem.grid[row][column] === 1 ? 0 : 1;
  }

  private loadSolution(): void {
    if (this.problem.id) {
      this.solutionsClient.getSolution(this.problem.id)
        .subscribe({
          next: (solution: LightsOutSolution) => {
            this.solution = solution;
          }
        });
    }
  }
}
