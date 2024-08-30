import { Component, inject, ViewEncapsulation } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./header/header.component";
import { ErrorDialogComponent } from "./error-dialog/error-dialog.component";
import { ErrorService } from "./error-dialog/error.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,
    HeaderComponent,
    RouterLink, ErrorDialogComponent, RouterLinkActive
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'capacity-planning-tool';

  private errorService = inject(ErrorService);

  error = this.errorService.error;
  additionalMessage = this.errorService.additionalMessage;

  constructor() {
  }



}
