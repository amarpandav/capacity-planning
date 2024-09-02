import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private _error = signal('');
  private _additionalError = signal('');

  error = this._error.asReadonly();
  additionalMessage = this._additionalError.asReadonly();

  showError(errorStatus: number, message: string, additionalMessage?: string) {
    /*console.log(message);
    console.log(errorStatus);*/
    if(errorStatus === 0 && !additionalMessage) {
      additionalMessage = "Not able to connect to backend. Connection refused!"
    }
    //console.log(additionalMessage);
    console.log("errorStatus: "+ errorStatus, ", message: "+ message, ", additionalMessage: "+additionalMessage);

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
