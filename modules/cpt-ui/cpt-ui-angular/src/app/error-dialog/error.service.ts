import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private _error = signal('');
  private _additionalError = signal('');

  error = this._error.asReadonly();
  additionalMessage = this._additionalError.asReadonly();

  showError(message: string, additionalMessage?: string) {
    console.log(message);
    console.log(additionalMessage);
    this._error.set(message);
    if(additionalMessage){
      this._additionalError.set(additionalMessage);
    }
  }

  clearError() {
    this._error.set('');
    this._additionalError.set('');
  }
}
