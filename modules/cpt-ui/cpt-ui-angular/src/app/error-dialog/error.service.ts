import { Injectable, signal } from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private _error = signal('');
  private _additionalError = signal('');

  error = this._error.asReadonly();
  additionalMessage = this._additionalError.asReadonly();


  showError(error: HttpErrorResponse, message: string) {
    let additionalMessage = error.error != undefined ? error.error.message: error.message;

    /*console.log(message);
    console.log(errorStatus);*/
    if(error.status === 0 && !additionalMessage) {
      additionalMessage = "Not able to connect to backend. Connection refused!"
    }
    //console.log(additionalMessage);
    console.log("errorStatus: "+ error.status, ", message: "+ message, ", additionalMessage: "+additionalMessage);

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
