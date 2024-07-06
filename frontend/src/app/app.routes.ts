import { Routes } from '@angular/router';
import { ProblemListComponent } from "./pages/problem-list/problem-list.component";
import { ProblemBuilderComponent } from "./pages/problem-builder/problem-builder.component";
import { ErrorComponent } from "./pages/error/error.component";

export const routes: Routes = [
  { path: '', redirectTo: '/problem-list', pathMatch: 'full' },
  { path: 'problem-list', component: ProblemListComponent },
  { path: 'problem-builder', component: ProblemBuilderComponent },
  { path: 'error', component: ErrorComponent },
  { path: '**', redirectTo: 'error' }
];
