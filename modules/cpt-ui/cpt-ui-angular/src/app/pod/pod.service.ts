import {Injectable} from "@angular/core";
import {catchError, map, Observable, of, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ErrorService} from "../error-dialog/error.service";
import {environment} from '../../environments/environment';
import {EntityId} from "../scheduler/models/entityId.model";
import {PodDto} from "../scheduler/models/pod/pod.model";
import {POD_TEST_DATA} from "../../testdata/pod/pod.test-data";

const PRODUCE_UI_TEST_DATA = false;

@Injectable({providedIn: 'root'})
export class PodService {

    constructor(private httpClient: HttpClient, private errorService: ErrorService) {
    }

    //dialog is displayed inside AppComponent.ts
    //TODO : Pod watchers are still pending
    findMyPods(userEntityId: EntityId<string>): Observable<PodDto[]> {
        return this.httpClient.get<{
            pods: PodDto[]
        }>(`${environment.apiUrl}/my-pods/` + userEntityId.uuid + '/my-pod-member-pods')
            .pipe(
                map((resBody) => {
                    //console.log("PodService.findMyPods().resBody:" + JSON.stringify(resBody))
                    return resBody.pods
                }),
                //map( (resBody) => resBody.places),
                catchError((error: HttpErrorResponse) => {
                    if (PRODUCE_UI_TEST_DATA) {
                        //return [new MyPodInfoDto(new EntityId<string>("64E34204E10246CFA5BEB12E077ABA11"), "AURA-test")];
                        /*let pods: MyPodInfoDto[] = [];
                        pods.push(new MyPodInfoDto(new EntityId<string>("64E34204E10246CFA5BEB12E077ABA11"), "AURA-test"))*/
                        return of(POD_TEST_DATA);
                    } else {
                        //console.log(JSON.stringify(error));
                        this.errorService.showError(error, 'Failed to perform findUsers()')
                        return throwError(() => new Error('Something went wrong : ' + error.message))
                    }
                })
            );
    }

    findRelatedPods(podEntityId: EntityId<string>): Observable<PodDto[]> {
        return this.httpClient.get<{
            pods: PodDto[]
        }>(`${environment.apiUrl}/pods/` + podEntityId.uuid + '/related-pods')
            .pipe(
                map((resBody) => {
                    console.log("PodService.findRelatedPods().resBody:" + JSON.stringify(resBody))
                    return resBody.pods
                }),
                //map( (resBody) => resBody.places),
                catchError((error: HttpErrorResponse) => {
                    if (PRODUCE_UI_TEST_DATA) {
                        //return [new MyPodInfoDto(new EntityId<string>("64E34204E10246CFA5BEB12E077ABA11"), "AURA-test")];
                        /*let pods: MyPodInfoDto[] = [];
                        pods.push(new MyPodInfoDto(new EntityId<string>("64E34204E10246CFA5BEB12E077ABA11"), "AURA-test"))*/
                        return of(POD_TEST_DATA);
                    } else {
                        //console.log(JSON.stringify(error));
                        this.errorService.showError(error, 'Failed to perform findRelatedPods()')
                        return throwError(() => new Error('Something went wrong : ' + error))
                    }
                })
            );
    }

}
