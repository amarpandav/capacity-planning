import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from '../../environments/environment';
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AppService {

    constructor(private http: HttpClient) {
    }

    getVersion(): Observable<any> {
        const url = `${environment.apiUrl}/application/version`;
        return this.http.get(url);
    }
}
