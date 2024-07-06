import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError, Observable, pipe, throwError } from "rxjs";
import { LightsOutSolution } from "../../shared/model/lights-out-solution.model";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class SolutionsClient {

  private url: string = '/api/solutions';

  constructor(private http: HttpClient, private router: Router) {
  }

  getSolution(problemId: string): Observable<LightsOutSolution> {
    return this.http.get<LightsOutSolution>(`${this.url}/problem/${problemId}`)
      .pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Error fetching problems', error);
        this.router.navigate(['/error']);
        return throwError(() => new Error(error.message));
      })
    );
  }
}
