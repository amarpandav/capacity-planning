import {Injectable} from "@angular/core";
import {catchError, map, Observable, of, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpParams, HttpResponse} from "@angular/common/http";
import {ErrorService} from "../error-dialog/error.service";
import {POD_ASSIGNMENT_VIEW_TEST_DATA} from "../../testdata/scheduler/pod-assignment-view.test-data";
import {DayHeaderDto} from "../scheduler-header/day-header.model";
import {AssignmentDto} from "./models/pod-assignment/assignment.model";
import {AvailabilityType} from "./models/availability/availability.enum";
import {PodAssignmentDto} from "./models/pod-assignment/pod-assignment.model";
import {DateUtils} from "../utils/DateUtils";
import {SchedulerSettingsDto} from "../scheduler-settings/scheduler.settings.model";
import {SchedulerHeaderService} from "../scheduler-header/scheduler-header.service";
import {environment} from '../../environments/environment';
import {PodAssignmentViewDto} from "./models/pod-assignment/pod-assignment-view.model";
import {EntityId} from "./models/entityId.model";
import {UserAssignmentDto} from "./models/pod-assignment/user-assignment.model";
import {PodAssignmentCreateRequestDto} from "./models/pod-assignment-request/pod-assignment-create-request.model";

const PRODUCE_UI_TEST_DATA = true;

@Injectable({providedIn: 'root'})
export class SchedulerService {

    constructor(private httpClient: HttpClient, private errorService: ErrorService, private schedulerHeaderService: SchedulerHeaderService) {
    }

    //dialog is displayed inside AppComponent.ts
    findMyPodAssignments(mySelectedPodEntityId: EntityId<string>, schedulerSettings: SchedulerSettingsDto): Observable<UserAssignmentDto[]> {
        const httpParams = new HttpParams()
            .set('startDate', DateUtils.formatToISODate(schedulerSettings.startDate))
            .set('endDate', DateUtils.formatToISODate(schedulerSettings.endDate));

        return this.httpClient.get<{
            assignments: UserAssignmentDto[]
        }>(`${environment.apiUrl}/pods/` + mySelectedPodEntityId.uuid + `/assignments`, {params: httpParams})
            .pipe(
                map((resBody) => {
                    //console.log("findMyPodAssignments(): resBody :" + JSON.stringify(resBody))
                    return resBody.assignments
                }),
                //map( (resBody) => resBody.places),
                catchError((error: HttpErrorResponse) => {
                    if (PRODUCE_UI_TEST_DATA) {

                        let testDataObservable = of(JSON.parse(JSON.stringify(POD_ASSIGNMENT_VIEW_TEST_DATA)));//if you don't deep clone data gets appended.
                        testDataObservable.subscribe((podAssignmentViewDto: PodAssignmentViewDto) => {
                            //console.log("findPodAssignments.catchError loading UI Test data:" + JSON.stringify(podAssignmentViewDto))
                            //console.log("findPodAssignments.catchError loading UI Test data:" + podAssignmentViewDto.userAssignments[0].podAssignments.length)
                            this.populateSchedulerTestData(schedulerSettings, podAssignmentViewDto);
                        });
                        return testDataObservable;
                    } else {
                        this.errorService.showError(error, 'Failed to perform findPodAssignments')
                        return throwError(() => new Error('Something went wrong : ' + error))
                    }
                })
            );
    }

    createPodAssignmentRequest(podAssignmentCreateRequestDto: PodAssignmentCreateRequestDto): Observable<any> {
        return this.httpClient.post<{
            httpResponse: HttpResponse<any>
        }>(`${environment.apiUrl}/pods/` + podAssignmentCreateRequestDto.podId + `/assignments`, podAssignmentCreateRequestDto)
            .pipe(
                map((resBody) => {
                    //console.log("createPodAssignmentRequest(): resBody :" + resBody);
                    //return resBody.assignments
                    //return resBody.httpResponse
                }),
                //map( (resBody) => resBody.places),
                catchError((error: HttpErrorResponse) => {
                        this.errorService.showError(error, 'Failed to perform createPodAssignmentRequest');
                        return throwError(() => new Error('Something went wrong : ' + error));

                })
            );
    }

    /**
     * Workaround: only temporary solution. In reality all this must come from backend.
     * Mark remaining capacity as available or public holiday
     * @private
     */
    private populateSchedulerTestData(schedulerSettings: SchedulerSettingsDto, podAssignmentView: PodAssignmentViewDto) {

        let schedulerHeaderDto = this.schedulerHeaderService.findSchedulerHeader(schedulerSettings);

        //Adjust all months of test data as per scheduler settings. When day matches replace the month as per scheduler settings
        var dateAdjusted: number[] = [];
        schedulerHeaderDto.dayHeaders.forEach((dayHeaderDto: DayHeaderDto) => {
            podAssignmentView?.userAssignments.forEach((userAssignment: UserAssignmentDto) => {
                let headerDateAsStr = DateUtils.formatToISODate(dayHeaderDto.day);

                userAssignment.podAssignments?.forEach((podAssignmentDto: PodAssignmentDto) => {

                    if (dayHeaderDto.day.getDate() === podAssignmentDto.day?.getDate() && dateAdjusted.indexOf(dayHeaderDto.day.getDate()) < 0) {
                        podAssignmentDto.day = dayHeaderDto.day;
                        podAssignmentDto.uuid = headerDateAsStr;

                        dateAdjusted.push(dayHeaderDto.day.getDate()); // otherwise last month gets assignments instead of first.
                    }
                });
            })
        });

        schedulerHeaderDto.dayHeaders.forEach((dayHeaderDto: DayHeaderDto) => {
            /*this.podMemberCapacities.forEach( (podMemberCapacityDto: PodMemberCapacityDto) => {
              let headerDateAsStr = this.datePipe.transform(dayHeaderDto.day, AppConstants.DATE_FORMAT);
              let find = podMemberCapacityDto.podAssignments?.find(capacityDto => capacityDto.day === headerDateAsStr);
            });*/
            podAssignmentView?.userAssignments.forEach((userAssignment: UserAssignmentDto) => {
                //let headerDateAsStr = "" + this.datePipe.transform(dayHeaderDto.day, AppConstants.DATE_FORMAT);
                let headerDateAsStr = DateUtils.formatToISODate(dayHeaderDto.day);

                let podAssignmentDto = userAssignment.podAssignments?.find(podAssignmentDto => DateUtils.formatToISODate(podAssignmentDto.day) === headerDateAsStr);

                if (podAssignmentDto) {
                    //capacity for this day do exists
                    //podAssignmentDto.day = DateUtils.parseISODate(podAssignmentDto.dayAsStr);//This is needed because constructor is not called when we use schedulerViews: SchedulerViewDto[] = SCHEDULER_VIEW_TEST_DATA
                    //capacity for this day exists, check if its complete otherwise complete it
                    //completing it means, morning and afternoon must be filled in with either booking or availability
                    //console.log("userCapacityDto found: " + JSON.stringify(userCapacityDto));
                    /*console.log("userCapacityDto.userBookedCapacity?.morningPod: " + userCapacityDto.userBookedCapacity?.morningPod);
                    console.log("userCapacityDto.userBookedCapacity?.afternoonPod: " + userCapacityDto.userBookedCapacity?.afternoonPod);
                    console.log("userCapacityDto.userAvailableCapacity?.morningAvailability: " + userCapacityDto.userAvailableCapacity?.morningAvailability);
                    console.log("userCapacityDto.userAvailableCapacity?.afternoonAvailability: " + userCapacityDto.userAvailableCapacity?.afternoonAvailability);*/
                    if (!podAssignmentDto.morning) {
                        //morning do not exist hence mark it as available
                        if (DateUtils.isWeekend(dayHeaderDto.day)) {
                            podAssignmentDto.morning = new AssignmentDto(AvailabilityType.PUBLIC_HOLIDAY);
                        } else {
                            podAssignmentDto.morning = new AssignmentDto(AvailabilityType.AVAILABLE);
                        }

                    } else if (!podAssignmentDto.afternoon) {
                        //afternoon do not exist hence mark it as available
                        if (DateUtils.isWeekend(dayHeaderDto.day)) {
                            podAssignmentDto.afternoon = new AssignmentDto(AvailabilityType.PUBLIC_HOLIDAY);
                        } else {
                            podAssignmentDto.afternoon = new AssignmentDto(AvailabilityType.AVAILABLE);
                        }
                    }
                } else {
                    //availability for this day do not exist, complete it now
                    let morning;
                    let afternoon

                    if (DateUtils.isWeekend(dayHeaderDto.day)) {
                        //console.log(dayHeaderDto.day +"is holiday");
                        morning = new AssignmentDto(AvailabilityType.PUBLIC_HOLIDAY);
                        afternoon = new AssignmentDto(AvailabilityType.PUBLIC_HOLIDAY)
                        //console.log(JSON.stringify(availableFullDayAvailabilityDto));
                    } else {
                        morning = new AssignmentDto(AvailabilityType.AVAILABLE)
                        afternoon = new AssignmentDto(AvailabilityType.AVAILABLE);
                    }
                    let podAssignmentDto = new PodAssignmentDto(headerDateAsStr, dayHeaderDto.day, morning, afternoon);
                    userAssignment.podAssignments?.push(podAssignmentDto)
                }

                //sort podAssignments otherwise manually added capacities would be pushed at the end of the list.
                userAssignment.podAssignments?.sort((a: PodAssignmentDto, b: PodAssignmentDto) => {
                    if (a.day && b.day) {
                        return a?.day > b?.day ? 1 : -1;
                    }
                    return 1;
                });
            });
        });

        //console.log("schedulerViews found: " + JSON.stringify(this.schedulerViews));
    }


}
