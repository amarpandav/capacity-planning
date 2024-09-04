import {AfterViewInit, Component, DestroyRef, ElementRef, OnInit, viewChild} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {SchedulerSettingsDto} from "../scheduler-settings/scheduler.settings.model";
import {PodDto} from "./models/pod/pod.model";
import {AvailabilityDto} from "./models/availability/availability.model";
import {UserDto} from "./models/user/user.model";
import {AVAILABILITY_TEST_DATA} from "../../testdata/availability/availability.test-data";
import {AvailabilityType} from "./models/availability/availability.enum";
import {DateUtils} from '../utils/DateUtils';
import {USER_PODS_TEST_DATA} from "../../testdata/user/user-pods.test-data";
import {UserPodsDto} from "./models/user/user-pods.model";
import {UserPodWatchersDto} from "./models/user/user-pod-watchers.model";
import {USER_POD_WATCHERS_TEST_DATA} from "../../testdata/user/user-pod-watchers.test-data";
import {AssignmentDto} from "./models/pod-assignment/assignment.model";
import {isAfternoon, isMorning, TimeSlot} from "./models/pod-assignment/time-slot.enum";
import {POD_DETAILS_TEST_DATA} from "../../testdata/pod/pod-details.test-data";
import {PodDetailsDto} from "./models/pod/pod-details.model";
import {POD_TEST_DATA} from "../../testdata/pod/pod.test-data";
import {
    PodAssignmentCreateRequestDayTemp,
    PodAssignmentCreateRequestDto,
    PodAssignmentCreateRequestTemp
} from "./models/pod-assignment-request/pod-assignment-create-request.model";
import {SchedulerService} from "./scheduler.service";
import {Subscription} from "rxjs";
import {SchedulerHeaderDto} from "../scheduler-header/scheduler-header.model";
import {SchedulerHeaderService} from "../scheduler-header/scheduler-header.service";
import {UserComponent} from "../user/user.component";
import {UserListComponent} from "../user-list/user-list.component";
import {UserViewingBoxComponent} from "../user-viewing-box/user-viewing-box.component";
import {SchedulerSettingsComponent} from "../scheduler-settings/scheduler-settings.component";
import {UserAssignmentDto} from "./models/pod-assignment/user-assignment.model";


@Component({
    selector: 'app-scheduler',
    standalone: true,
    imports: [
        FormsModule,
        DatePipe,
        UserComponent,
        UserListComponent,
        UserViewingBoxComponent,
        SchedulerSettingsComponent
    ],
    templateUrl: './scheduler.component.html',
    styleUrl: './scheduler.component.scss'
})
export class SchedulerComponent implements OnInit, AfterViewInit {

    schedulerSettings: SchedulerSettingsDto;

    schedulerHeader: SchedulerHeaderDto;

    protected readonly availability: AvailabilityDto[] = AVAILABILITY_TEST_DATA;

    protected readonly userPods: UserPodsDto[] = USER_PODS_TEST_DATA;
    protected readonly userPodWatchers: UserPodWatchersDto[] = USER_POD_WATCHERS_TEST_DATA;

    protected readonly pods: PodDto[] = POD_TEST_DATA;
    protected readonly podDetails: PodDetailsDto[] = POD_DETAILS_TEST_DATA;

    //protected readonly schedulerViews: PodViewDto[] = SCHEDULER_VIEW_TEST_DATA;
    //protected podAssignmentViewDto?: PodAssignmentViewDto;
    protected userAssignments?: UserAssignmentDto[]

    mySelectedPod?: PodDto

    selectedPodToAssign?: PodDto;

    private podAssignmentDialogEl = viewChild.required<ElementRef<HTMLDialogElement>>('bookingDialog');

    //podAssignmentCreateRequest: PodAssignmentToSave | undefined;
    podAssignmentCreateRequestTempStart: PodAssignmentCreateRequestTemp | undefined;
    podAssignmentCreateRequestTempEnd: PodAssignmentCreateRequestTemp | undefined;
    //podAssignmentCreateRequest: PodAssignmentCreateRequestDto | undefined;

    //podAssignmentCreateRequestUsers: Set<string>  = new Set();
    podAssignmentCreateRequestUsers: string[] = [];
    //podAssignmentCreateRequestDays: Date[] = [];
    podAssignmentCreateRequestDaysTemp: PodAssignmentCreateRequestDayTemp[] = [];

    selectedUser?: UserDto;

    isWeekEnd(date: string): boolean {
        let day = new Date(date).getDay();
        return (day === 0 || day === 6)
    }


    constructor(private datePipe: DatePipe,
                private destroyRef: DestroyRef,
                private schedulerService: SchedulerService,
                private schedulerHeaderService: SchedulerHeaderService) {
        this.schedulerSettings = SchedulerSettingsDto.newDefaultInstance();
        this.schedulerHeader = this.schedulerHeaderService.findSchedulerHeader(this.schedulerSettings);
    }

    onSelectedUserEventListener(selectedUser: UserDto) {
        // console.log("I am (SchedulerComponent) consuming emitted user as an Object: " + JSON.stringify(selectedUser));
        this.selectedUser = selectedUser;
    }

    onSelectedPodToAssignEventListener(podDto: PodDto) {
         console.log("I am (SchedulerComponent) consuming emitted selected pod to assign as an Object: " + JSON.stringify(podDto));
        this.selectedPodToAssign = podDto;
    }

    ngOnInit(): void {
        //this.isLoadingPlaces.set(true);
        //this.findMyPodAssignments();
    }

    private findMyPodAssignments() {
        if (this.mySelectedPod) {
            this.userAssignments = undefined;
            const subscription1 = this.schedulerService.findMyPodAssignments(this.mySelectedPod.entityId, this.schedulerSettings)
                .subscribe({
                        next: (userAssignments) => {
                            //console.log(podAssignmentView);
                            this.userAssignments = userAssignments;
                            // console.log("SchedulerComponent.findPodAssignments(): Data is: "+JSON.stringify(this.userAssignments));
                        }/*,Not needed as its handled in the service
                    error: (error) => {
                        this.error.set(error.message);
                        this.toastrService.error('Something went wrong: ' + error.message);
                    },
                    complete: () => {
                        this.isLoadingPlaces.set(false);
                    }*/
                    }
                );

            //Destroy is optional
            this.destroySubscription(subscription1);
        }

    }

    private destroySubscription(subscription: Subscription) {
        this.destroyRef.onDestroy(() => {
            subscription.unsubscribe(); //technical this is not required but its a good idea
        });
    }

    protected readonly JSON = JSON;

    onSchedulerSettingsEventListener(schedulerSettings: SchedulerSettingsDto) {
        this.schedulerSettings = schedulerSettings;

        //Recalculate the SchedulerDto
        this.schedulerHeader = this.schedulerHeaderService.findSchedulerHeader(this.schedulerSettings);
        this.findMyPodAssignments();
    }

    onSelectedMyPodEventListener(mySelectedPod: PodDto) {
        //console.log("onSelectedMyPodEntityIdEventListener received: "+ JSON.stringify(mySelectedPod));
        this.mySelectedPod = mySelectedPod;
        this.findMyPodAssignments();
    }

    protected readonly DateUtils = DateUtils;

    onDragStart($event: MouseEvent,
                clickedUserAssignment: UserAssignmentDto,
                userInAction: UserDto,
                timeSlotInAction: TimeSlot,
                assignmentInAction: AssignmentDto,
                podInAction?: PodDto,
                dayInAction?: Date | null) {
        if(this.selectedPodToAssign){
            console.log("onDragStart...");

            // @ts-ignore : dayInAction would never be null
            this.allocateUsersToMyPod(userInAction, dayInAction, timeSlotInAction, assignmentInAction);

            /*clickedUserAssignment.podAssignments.forEach( (podAssignment: PodAssignmentDto) => {
                podAssignment.morning.pod = this.selectedPodToAssign;
                podAssignment.afternoon.pod = this.selectedPodToAssign;
            });*/
            // @ts-ignore : dayInAction would never be null
            this.podAssignmentCreateRequestTempStart = new PodAssignmentCreateRequestTemp(userInAction, dayInAction, timeSlotInAction);
        }

    }

    onWhileDragging($event: MouseEvent,
                    userAssignment: UserAssignmentDto,
                    userInAction: UserDto,
                    timeSlotInAction: TimeSlot,
                    assignmentInAction: AssignmentDto,
                    pod: PodDto | undefined,
                    dayInAction: Date | null | undefined) {
        if (this.selectedPodToAssign && this.podAssignmentCreateRequestTempStart && !this.podAssignmentCreateRequestTempEnd) {
            console.log("whileDragging...");
            console.log("userInAction:" + JSON.stringify(userInAction));
            console.log("dayInAction:" + JSON.stringify(dayInAction));
            console.log("timeSlotInAction:" + JSON.stringify(timeSlotInAction));
            console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));

            // @ts-ignore : dayInAction would never be null
            this.allocateUsersToMyPod(userInAction, dayInAction, timeSlotInAction, assignmentInAction);
        }


    }

    onDragEnd($event: MouseEvent,
              clickedUserAssignment: UserAssignmentDto,
              userInAction: UserDto,
              timeSlotInAction: TimeSlot,
              assignmentInAction: AssignmentDto,
              podInAction?: PodDto,
              dayInAction?: Date | null) {
        if(this.selectedPodToAssign){
            console.log("onDragEnd...");

            // @ts-ignore : dayInAction would never be null
            this.allocateUsersToMyPod(userInAction, dayInAction, timeSlotInAction, assignmentInAction);

            // @ts-ignore : dayInAction would never be null
            this.podAssignmentCreateRequestTempEnd = new PodAssignmentCreateRequestTemp(userInAction, dayInAction, timeSlotInAction);

            this.preparePodAssignmentCreateRequest();
        }

    }

    private allocateUsersToMyPod(userInAction: UserDto, dayInAction: Date, timeSlotInAction: TimeSlot, assignmentInAction: AssignmentDto) {
        console.log("userInAction:" + JSON.stringify(userInAction));
        console.log("dayInAction:" + JSON.stringify(dayInAction));
        console.log("timeSlotInAction:" + JSON.stringify(timeSlotInAction));
        console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));
        console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));


        /*if (this.podAssignmentCreateRequestDays.indexOf(dayInAction) === -1) {
            //this.podAssignmentCreateRequestUsers.push(this.podAssignmentCreateRequestTempStart.userInAction.uuid);
            this.podAssignmentCreateRequestDays.push(dayInAction);
        }*/


        if (assignmentInAction.availabilityType === AvailabilityType.AVAILABLE) {
            //We allow to book only AVAILABLE slots otherwise we would allow overriding someone else's bookings which we do not want.
            assignmentInAction.availabilityType = AvailabilityType.POD_ASSIGNMENT;
            assignmentInAction.pod = this.selectedPodToAssign;

            if (this.podAssignmentCreateRequestUsers.indexOf(userInAction.entityId.uuid) === -1) {
                //this.podAssignmentCreateRequestUsers.push(this.podAssignmentCreateRequestTempStart.userInAction.uuid);
                this.podAssignmentCreateRequestUsers.push(userInAction.entityId.uuid);
            }

            /*let listHasDay = this.podAssignmentCreateRequestDays.some( (day) => DateUtils.formatToISODate(day) === DateUtils.formatToISODate(dayInAction));
            if(!listHasDay) {
                this.podAssignmentCreateRequestDays.push(dayInAction);
            }
            //sort request by date - this is must before sending request to backend
            this.podAssignmentCreateRequestDays?.sort((a: Date, b: Date) => {
                if (a && b) {
                    return a > b ? 1 : -1;
                }
                return 1;
            });*/
            let podAssignmentCreateRequestDayTemp = this.podAssignmentCreateRequestDaysTemp.find((requestDay) => DateUtils.formatToISODate(requestDay.day) === DateUtils.formatToISODate(dayInAction));
            if (!podAssignmentCreateRequestDayTemp) {
                //This is new day
                this.podAssignmentCreateRequestDaysTemp.push(new PodAssignmentCreateRequestDayTemp(dayInAction, isMorning(timeSlotInAction) ? timeSlotInAction : null, isAfternoon(timeSlotInAction) ? timeSlotInAction : null));
            } else {
                //This day already exists
                if (isMorning(timeSlotInAction)) {
                    //even if this timeslot is set we are overriding it here but it's fine, it doesn't really matter.
                    podAssignmentCreateRequestDayTemp.morningTimeSlot = timeSlotInAction;
                }
                if (isAfternoon(timeSlotInAction)) {
                    //even if this timeslot is set we are overriding it here but it's fine, it doesn't really matter.
                    podAssignmentCreateRequestDayTemp.afternoonTimeSlot = timeSlotInAction;
                }
            }
            //sort request by date - this is must before sending request to backend
            this.podAssignmentCreateRequestDaysTemp?.sort((a: PodAssignmentCreateRequestDayTemp, b: PodAssignmentCreateRequestDayTemp) => {
                if (a.day && b.day) {
                    return a.day > b.day ? 1 : -1;
                }
                return 1;
            });

            //console.log("podAssignmentCreateRequestUsersCloned:" + JSON.stringify(podAssignmentCreateRequestUsersCloned));
            //console.log("this.podAssignmentCreateRequestUsers:" + JSON.stringify(this.podAssignmentCreateRequestUsers));
            //console.log("this.podAssignmentCreateRequestDays:" + JSON.stringify(this.podAssignmentCreateRequestDays));
            //console.log("this.podAssignmentCreateRequestDaysTemp:" + JSON.stringify(this.podAssignmentCreateRequestDaysTemp));

        }
    }

    private preparePodAssignmentCreateRequest() {
        if (this.selectedPodToAssign && this.podAssignmentCreateRequestTempStart && this.podAssignmentCreateRequestTempStart.isDataValid && this.podAssignmentCreateRequestTempEnd && this.podAssignmentCreateRequestTempEnd.isDataValid) {

            /*this.podAssignmentCreateRequestUsers.push(this.podAssignmentCreateRequestTempStart.userInAction.uuid);
            //Is start and end user different, if yes then we need to select all in-between users
            if (this.podAssignmentCreateRequestTempStart.userInAction.gpin !== this.podAssignmentCreateRequestTempEnd.userInAction.gpin) {
                for (let userAssignment of this.podAssignmentViewDto.userAssignments) {
                    this.podAssignmentCreateRequestUsers.push(userAssignment.user.uuid);
                    if (userAssignment.user.gpin === this.podAssignmentCreateRequestTempEnd?.userInAction.gpin) {
                        break;
                    }
                }
            }*/
            //usersToAssignToAPodAsUuids = [...new Set(usersToAssignToAPod.map( (u) => u.uuid))]//remove duplicates;
            //this.podAssignmentCreateRequestUsers = [...new Set(this.podAssignmentCreateRequestUsers)]//remove duplicates;
            //console.log("usersToAssignToAPod: "+ JSON.stringify(usersToAssignToAPod));
            //[].concat(this.podAssignmentCreateRequestUsers)
            //let podAssignmentToSaveTempStartCloned = {...this.podAssignmentCreateRequestTempStart}; //lets clone because we are resetting this at the end
            //let podAssignmentToSaveTempEndCloned = {...this.podAssignmentCreateRequestTempEnd};

            let podAssignmentCreateRequest = new PodAssignmentCreateRequestDto(
                this.selectedPodToAssign.entityId.uuid,
                this.podAssignmentCreateRequestUsers,
                this.podAssignmentCreateRequestTempStart.dayInAction,
                this.podAssignmentCreateRequestTempStart.timeSlotInAction,
                this.podAssignmentCreateRequestTempEnd.dayInAction,
                this.podAssignmentCreateRequestTempEnd.timeSlotInAction);

            //this.podAssignmentDialogEl().nativeElement.showModal();
            this.destroyPodAllocationCreateRequest();

            console.log("#####################podAssignmentCreateRequest#####################" + JSON.stringify(podAssignmentCreateRequest));
            this.createPodAssignmentCreateRequest(podAssignmentCreateRequest)
        }
    }

    private createPodAssignmentCreateRequest(podAssignmentCreateRequest: PodAssignmentCreateRequestDto) {
        const subscription1 = this.schedulerService.createPodAssignmentRequest(podAssignmentCreateRequest)
            .subscribe({
                    next: () => {
                        //console.log("whatever:" + JSON.stringify(whatever));
                        //reload assignments after booking.
                        this.findMyPodAssignments();
                    }
                }
            );

        //Destroy is optional
        this.destroySubscription(subscription1);
    }

    ngAfterViewInit(): void {
        //this.bookingDialogEl().nativeElement.showModal();
    }

    onBookingDialogCancel() {
        this.podAssignmentDialogEl().nativeElement.close();
    }

    onBookingSave() {

    }

    protected readonly TimeSlot = TimeSlot;


    destroyPodAllocationCreateRequest() {
        //<div (mouseleave)="destroyPodAllocationCreateRequest()"> is must because in case user drags outside scheduler we want to throw everything and not save anything.
        if (this.podAssignmentCreateRequestTempStart) {
            //destroy only when we are inside booking mode.
            console.log("destroyPodAllocationCreateRequest");
            //rest all class level variables after request is sent.
            this.podAssignmentCreateRequestTempStart = undefined;
            this.podAssignmentCreateRequestTempEnd = undefined;
            this.podAssignmentCreateRequestUsers = [];
            this.podAssignmentCreateRequestDaysTemp = [];
            this.userAssignments = [];
            this.findMyPodAssignments();
        }

    }


}
