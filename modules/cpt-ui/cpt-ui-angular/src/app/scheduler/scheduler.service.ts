import {Injectable} from "@angular/core";
import {catchError, map, of, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {PodAssignmentViewDto} from "./models/pod-view/pod-view.model";
import {ErrorService} from "../error-dialog/error.service";
import {POD_ASSIGNMENT_VIEW_TEST_DATA} from "../../testdata/scheduler/pod-assignment-view.test-data";
import {DayHeaderDto} from "../scheduler-header/day-header.model";
import {PodAssignmentWrapperDto} from "./models/pod-assignment/pod-assignment-wrapper.model";
import {AssignmentDto} from "./models/pod-assignment/assignment.model";
import {AvailabilityType} from "./models/availability/availability.enum";
import {PodAssignmentDto} from "./models/pod-assignment/pod-assignment.model";
import {DateUtils} from "../utils/DateUtils";
import {SchedulerSettingsDto} from "./models/settings/scheduler.settings.model";
import {PodDto} from "./models/pod/pod.model";
import {SchedulerHeaderService} from "../scheduler-header/scheduler-header.service";
import {environment} from '../../environments/environment';

const PRODUCE_UI_TEST_DATA = true;

@Injectable({providedIn: 'root'})
export class SchedulerService {

    constructor(private httpClient: HttpClient, private errorService: ErrorService, private schedulerHeaderService: SchedulerHeaderService) {
    }

    //dialog is displayed inside AppComponent.ts
    findPodAssignments(currentPodToView: PodDto, schedulerSettings: SchedulerSettingsDto) {
        /*var searchParams = {
            //"podUuid": currentPodToView.uuid,
            "startDate": schedulerSettings.startDate,
            "endDate": schedulerSettings.endDate,
        };*/

        /*const httpParams = new HttpParams()
                                            .set('startDate', '2024-08-01')
                                            .set('endDate', '2024-10-01');*/
        const httpParams = new HttpParams()
            .set('startDate', DateUtils.formatToISODate(schedulerSettings.startDate))
            .set('endDate', DateUtils.formatToISODate(schedulerSettings.endDate));

        //TODO not working
        return this.httpClient.get<{
            podAssignmentView: PodAssignmentViewDto
        }>(`${environment.apiUrl}/pods/578D06828DAD49B780A560E017CA28D3/assignments`, { params: httpParams })
            .pipe(
                map((resBody) => {
                    console.log("resBody.podAssignmentView:" + JSON.stringify(resBody.podAssignmentView))
                    return resBody.podAssignmentView
                }),
                //map( (resBody) => resBody.places),
                catchError((error: HttpErrorResponse) => {
                    if (PRODUCE_UI_TEST_DATA) {
                        let testDataObservable = of(POD_ASSIGNMENT_VIEW_TEST_DATA);
                        testDataObservable.subscribe((podAssignmentViewDto: PodAssignmentViewDto) => {
                            console.log("findPodAssignments.catchError loading UI Test data:" + JSON.stringify(podAssignmentViewDto))
                            this.populateSchedulerTestData(schedulerSettings, podAssignmentViewDto)
                        });
                        return testDataObservable;
                    } else {
                        this.errorService.showError(error.status, 'Failed to perform findPodAssignments', error.error.message)
                        return throwError(() => new Error('Something went wrong : ' + error.message))
                    }
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

        schedulerHeaderDto.dayHeaders.forEach((dayHeaderDto: DayHeaderDto) => {
            /*this.podMemberCapacities.forEach( (podMemberCapacityDto: PodMemberCapacityDto) => {
              let headerDateAsStr = this.datePipe.transform(dayHeaderDto.day, AppConstants.DATE_FORMAT);
              let find = podMemberCapacityDto.podAssignments?.find(capacityDto => capacityDto.day === headerDateAsStr);
            });*/
            podAssignmentView?.podAssignmentWrappers.forEach((podAssignmentWrapper: PodAssignmentWrapperDto) => {
                //let userDto = schedulerViewDto.user;

                //let headerDateAsStr = "" + this.datePipe.transform(dayHeaderDto.day, AppConstants.DATE_FORMAT);
                let headerDateAsStr = DateUtils.formatToISODate(dayHeaderDto.day);
                let podAssignmentDto = podAssignmentWrapper.podAssignments?.find(podAssignmentDto => podAssignmentDto.dayAsStr === headerDateAsStr);

                if (podAssignmentDto) {
                    //capacity for this day do exists
                    podAssignmentDto.day = DateUtils.parseISODate(podAssignmentDto.dayAsStr);//This is needed because constructor is not called when we use schedulerViews: SchedulerViewDto[] = SCHEDULER_VIEW_TEST_DATA
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
                    let podAssignmentDto = new PodAssignmentDto(headerDateAsStr, morning, afternoon, headerDateAsStr, null);
                    podAssignmentWrapper.podAssignments?.push(podAssignmentDto)
                }

                //sort podAssignments otherwise manually added capacities would be pushed at the end of the list.
                podAssignmentWrapper.podAssignments?.sort((a: PodAssignmentDto, b: PodAssignmentDto) => {
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
