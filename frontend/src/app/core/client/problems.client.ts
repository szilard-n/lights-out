import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpParams } from "@angular/common/http";
import { LightsOutProblem } from "../../shared/model/lights-out-problem.model";
import { PaginatedResponse } from "../../shared/model/paginated-response.model";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class ProblemsClient {

  private url: string = '/api/problems';

  constructor(private http: HttpClient, private router: Router) {
  }

  getProblems(pageIndex: number, pageSize: number): Observable<PaginatedResponse<LightsOutProblem>> {
    let params = new HttpParams()
      .set('page', pageIndex.toString())
      .set('size', pageSize.toString());
    return this.http.get<PaginatedResponse<LightsOutProblem>>(this.url, { params })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          console.error('Error fetching problems', error);
          this.router.navigate(['/error']);
          return throwError(() => new Error(error.message));
        })
      );
  }

  createProblem(problem: LightsOutProblem): Observable<any> {
    return this.http.post(this.url, problem)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 422) {
            // If the status is 422, forward the error to the caller
            return throwError(() => new Error(error.message));
          } else {
            this.router.navigate(['/error']);
            return throwError(() => new Error(error.message));
          }
        })
      );
  }
}
