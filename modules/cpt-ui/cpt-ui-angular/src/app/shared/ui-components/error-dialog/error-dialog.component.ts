import { AfterViewInit, Component, ElementRef, inject, input, viewChild } from '@angular/core';
import { ErrorService } from "./error.service";

@Component({
    selector: 'app-error-dialog',
    standalone: true,
    templateUrl: './error-dialog.component.html',
    styleUrl: './error-dialog.component.css',
    imports: []
})
export class ErrorDialogComponent implements AfterViewInit {
  title = input<string>();
  message = input<string>();
  additionalMessage = input<string>();

  private errorService = inject(ErrorService);

  private dialogEl = viewChild.required<ElementRef<HTMLDialogElement>>('dialog');

  ngAfterViewInit(): void {
    this.dialogEl().nativeElement.showModal();
  }

  onClearError() {
    this.errorService.clearError();
  }


}
