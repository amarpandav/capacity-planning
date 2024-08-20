import { Injectable } from '@angular/core';
import {Version} from "../model/version";
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {Observable} from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class VersionService {

  constructor(private http: HttpClient) {
  }

  getVersion(): Observable<Version> {
    const url = `${environment.base_url}/application/version`;
    return this.http.get(url);
  }
}
