import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatToolbar } from "@angular/material/toolbar";
import { MatDrawer, MatDrawerContainer, MatDrawerContent } from "@angular/material/sidenav";
import { MatListItem, MatNavList } from "@angular/material/list";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbar,
    MatDrawerContainer,
    MatDrawer,
    MatDrawerContent,
    MatNavList,
    MatListItem,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Lights Out';
  problemListPath = '/problem-list';
  problemBuilderPath = '/problem-builder';
}
