import { AfterContentInit, AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from "@angular/material/paginator";
import { NgForOf, NgIf } from "@angular/common";
import { ProblemGridComponent } from "../../components/problem-grid/problem-grid.component";
import { MatDivider } from "@angular/material/divider";
import { MatButton } from "@angular/material/button";
import { LightsOutProblem } from "../../shared/model/lights-out-problem.model";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { ProblemsClient } from "../../core/client/problems.client";
import { PaginatedResponse } from "../../shared/model/paginated-response.model";
import { delay, startWith, tap } from "rxjs";

@Component({
  selector: 'app-problem-list',
  standalone: true,
  imports: [
    MatPaginator,
    NgForOf,
    ProblemGridComponent,
    MatDivider,
    NgIf,
    MatButton,
    MatProgressSpinner
  ],
  templateUrl: './problem-list.component.html',
  styleUrls: ['./problem-list.component.css']
})
export class ProblemListComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  lightsOutProblems: LightsOutProblem[] = [];
  selectedLightsOutProblem: LightsOutProblem | null = null;
  originalSelectedProblem: LightsOutProblem | null = null;
  totalProblems: number = 0;
  showSolution: boolean = false;
  solutionButtonText: string = 'Show Solution';
  areProblemsLoading: boolean = false;

  constructor(private problemsClient: ProblemsClient) {
  }

  ngAfterViewInit() {
    this.paginator.page
      .pipe(
        startWith(null),
        delay(0),
        tap(() => this.loadProblems())
      ).subscribe()
  }

  loadProblems(): void {
    this.areProblemsLoading = true;
    this.problemsClient.getProblems(this.paginator.pageIndex, this.paginator.pageSize)
      .subscribe({
        next: (paginatedResponse: PaginatedResponse<LightsOutProblem>) => {
          this.lightsOutProblems = paginatedResponse.items;
          this.totalProblems = paginatedResponse.totalItems;
        },
        error: () => {
          this.areProblemsLoading = false;
        },
        complete: () => {
          this.areProblemsLoading = false;
        }
      });
  }

  viewProblem(problem: LightsOutProblem): void {
    this.originalSelectedProblem = JSON.parse(JSON.stringify(problem));
    this.selectedLightsOutProblem = JSON.parse(JSON.stringify(problem));
    this.showSolution = false;
    this.solutionButtonText = 'Show Solution';
  }

  resetProblem(): void {
    if (this.originalSelectedProblem) {
      this.selectedLightsOutProblem = JSON.parse(JSON.stringify(this.originalSelectedProblem));
    }
  }

  toggleSolution(): void {
    this.showSolution = !this.showSolution;
    this.solutionButtonText = this.showSolution ? 'Hide Solution' : 'Show Solution';
  }
}
