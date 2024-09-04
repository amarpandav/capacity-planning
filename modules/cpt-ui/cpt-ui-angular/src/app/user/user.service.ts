import {Injectable} from "@angular/core";
import {catchError, map, of, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ErrorService} from "../error-dialog/error.service";
import {UserDto} from "../scheduler/models/user/user.model";
import {USER_TEST_DATA} from "../../testdata/user/user.test-data";
import {environment} from '../../environments/environment';
import {UserSearchParameters} from "./user.search.parameters";

const PRODUCE_UI_TEST_DATA = true;

@Injectable({providedIn: 'root'})
export class UserService {

    constructor(private httpClient: HttpClient, private errorService: ErrorService) {
    }

    //dialog is displayed inside AppComponent.ts
    findUsers(userSearchParameters: UserSearchParameters) {
        return this.httpClient.post<{
            users: UserDto[]
        }>(`${environment.apiUrl}/users/findUsers`, userSearchParameters)
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
                        this.errorService.showError(error, 'Failed to perform findUsers()');
                        return throwError(() => new Error('Something went wrong : ' + error));
                    }
                })
            );
    }

}
