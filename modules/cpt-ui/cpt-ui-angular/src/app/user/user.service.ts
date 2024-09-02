import {Injectable} from "@angular/core";
import {catchError, map, of, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ErrorService} from "../error-dialog/error.service";
import {UserDto} from "../scheduler/models/user/user.model";
import {USER_TEST_DATA} from "../../testdata/user/user.test-data";
import {environment} from '../../environments/environment';

const PRODUCE_UI_TEST_DATA = false;

@Injectable({providedIn: 'root'})
export class UserService {

    constructor(private httpClient: HttpClient, private errorService: ErrorService) {
    }

    //dialog is displayed inside AppComponent.ts
    findUsers() {
        return this.httpClient.post<{
            users: UserDto[]
        }>(`${environment.apiUrl}/users/findUsers`, {})//TODO send UserSearchParameters
            .pipe(
                map((resBody) => {
                    //console.log("UserService.resBody.user:" + JSON.stringify(resBody.users))
                    return resBody.users
                }),
                //map( (resBody) => resBody.places),
                catchError((error: HttpErrorResponse) => {
                    if (PRODUCE_UI_TEST_DATA) {
                        return of(USER_TEST_DATA);
                    } else {
                        //console.log(JSON.stringify(error));
                        this.errorService.showError(error.status, 'Failed to perform findUsers()', error.error.message)
                        return throwError(() => new Error('Something went wrong : ' + error.message))
                    }
                })
            );
    }

}
