import { Component } from '@angular/core';
import { LightsOutProblem } from "../../shared/model/lights-out-problem.model";
import { GridSize } from "../../shared/model/grid-size.model";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatOption, MatSelect } from "@angular/material/select";
import { FormsModule } from "@angular/forms";
import { NgForOf, NgIf } from "@angular/common";
import { MatButton } from "@angular/material/button";
import { ProblemGridComponent } from "../../components/problem-grid/problem-grid.component";
import { ProblemsClient } from "../../core/client/problems.client";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-problem-builder',
  standalone: true,
  imports: [
    MatFormField,
    MatSelect,
    MatLabel,
    MatOption,
    FormsModule,
    NgForOf,
    MatButton,
    ProblemGridComponent,
    MatProgressSpinner,
    NgIf
  ],
  templateUrl: './problem-builder.component.html',
  styleUrl: './problem-builder.component.css'
})
export class ProblemBuilderComponent {
  gridSizes: GridSize[] = [
    { label: '3x3', size: 3 },
    { label: '4x4', size: 4 },
    { label: '5x5', size: 5 },
    { label: '6x6', size: 6 },
    { label: '7x7', size: 7 },
    { label: '8x8', size: 8 },
  ];

  selectedSize: number = 3;  // Default grid size
  problem: LightsOutProblem = this.createEmptyGrid();

  constructor(
    private problemsClient: ProblemsClient,
    private snackbar: MatSnackBar
  ) {
  }

  createEmptyGrid(): LightsOutProblem {
    const problemGrid: number[][] = Array.from(
      { length: this.selectedSize },
      () => Array(this.selectedSize).fill(0)
    );
    return { grid: problemGrid }
  }

  onGridSizeChange(): void {
    this.problem = this.createEmptyGrid();
  }

  submitProblem(): void {
    this.problemsClient.createProblem(this.problem)
      .subscribe({
        next: () => {
          this.showSnackbar('Problem solved and saved successfully!');
        },
        error: () => {
          this.showSnackbar('The submitted problem is unsolvable!');
        },
        complete: () => {
          this.problem = this.createEmptyGrid();
        }
      });
  }

  showSnackbar(message: string) {
    this.snackbar.open(message, 'Close', {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });
  }
}
