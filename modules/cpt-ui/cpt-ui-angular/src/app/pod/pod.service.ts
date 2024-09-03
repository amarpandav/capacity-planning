import {Injectable} from "@angular/core";
import {catchError, map, Observable, of, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ErrorService} from "../error-dialog/error.service";
import {environment} from '../../environments/environment';
import {EntityId} from "../scheduler/models/entityId.model";
import {MyPodInfoDto} from "./my-pod.model";

const PRODUCE_UI_TEST_DATA = true;

@Injectable({providedIn: 'root'})
export class PodService {

    constructor(private httpClient: HttpClient, private errorService: ErrorService) {
    }

    //dialog is displayed inside AppComponent.ts
    findMyPods(userEntityId: EntityId<string>): Observable<MyPodInfoDto[]> {
        return this.httpClient.get<{
            pods: MyPodInfoDto[]
        }>(`${environment.apiUrl}/mypods/` + userEntityId.uuid + '/my-pod-member-pods')
            .pipe(
                map((resBody) => {
                    //console.log("PodService.findMyPods().resBody:" + JSON.stringify(resBody))
                    return resBody.pods
                }),
                //map( (resBody) => resBody.places),
                catchError((error: HttpErrorResponse) => {
                    if (PRODUCE_UI_TEST_DATA) {
                        //return [new MyPodInfoDto(new EntityId<string>("64E34204E10246CFA5BEB12E077ABA11"), "AURA-test")];
                        let pods: MyPodInfoDto[] = [];
                        pods.push(new MyPodInfoDto(new EntityId<string>("64E34204E10246CFA5BEB12E077ABA11"), "AURA-test"))
                        return of(pods);
                    } else {
                        //console.log(JSON.stringify(error));
                        this.errorService.showError(error.status, 'Failed to perform findUsers()', error.error.message)
                        return throwError(() => new Error('Something went wrong : ' + error.message))
                    }
                })
            );
    }

}
