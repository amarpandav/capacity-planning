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
    PodAssignmentCreateRequestDto,
    DayToAssign, DayAndSlotToAssign
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
    //podAssignmentCreateRequest: PodAssignmentCreateRequestDto | undefined;

    //podAssignmentCreateRequestUsers: Set<string>  = new Set();
    usersToAssign: string[] = [];
    dayAndSlotToAssign: DayAndSlotToAssign[] = [];
    startDayToAssign: DayToAssign | undefined;
    endDayToAssign: DayToAssign | undefined;
    //podAssignmentCreateRequestDays: Date[] = [];
    //podAssignmentCreateRequestDaysTemp: PodAssignmentCreateRequestDayTemp[] = [];

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
        // console.log("I am (SchedulerComponent) consuming emitted selected pod to assign as an Object: " + JSON.stringify(podDto));
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
            //console.log("onDragStart...");

            // @ts-ignore : dayInAction would never be null
            this.addAssignment(userInAction, dayInAction, timeSlotInAction, assignmentInAction);

            /*clickedUserAssignment.podAssignments.forEach( (podAssignment: PodAssignmentDto) => {
                podAssignment.morning.pod = this.selectedPodToAssign;
                podAssignment.afternoon.pod = this.selectedPodToAssign;
            });*/
            // @ts-ignore : dayInAction would never be null
            this.startDayToAssign = new DayToAssign(dayInAction, timeSlotInAction);
        }

    }

    onWhileDragging($event: MouseEvent,
                    userAssignment: UserAssignmentDto,
                    userInAction: UserDto,
                    timeSlotInAction: TimeSlot,
                    assignmentInAction: AssignmentDto,
                    pod: PodDto | undefined,
                    dayInAction: Date | null | undefined) {
        if (this.selectedPodToAssign && this.startDayToAssign && !this.endDayToAssign) {
            //console.log("whileDragging...");

            // @ts-ignore : dayInAction would never be null
            this.addAssignment(userInAction, dayInAction, timeSlotInAction, assignmentInAction);
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
            //console.log("onDragEnd...");

            // @ts-ignore : dayInAction would never be null
            this.addAssignment(userInAction, dayInAction, timeSlotInAction, assignmentInAction);

            // @ts-ignore : dayInAction would never be null
            this.endDayToAssign = new DayToAssign(dayInAction, timeSlotInAction);

            this.preparePodAssignmentCreateRequest();
        }

    }

    private addAssignment(userInAction: UserDto, dayInAction: Date, timeSlotInAction: TimeSlot, assignmentInAction: AssignmentDto) {
       /* console.log("userInAction:" + JSON.stringify(userInAction));
        console.log("dayInAction:" + JSON.stringify(dayInAction));
        console.log("timeSlotInAction:" + JSON.stringify(timeSlotInAction));
        console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));*/

        /*if (this.podAssignmentCreateRequestDays.indexOf(dayInAction) === -1) {
            //this.podAssignmentCreateRequestUsers.push(this.podAssignmentCreateRequestTempStart.userInAction.uuid);
            this.podAssignmentCreateRequestDays.push(dayInAction);
        }*/

        let dayAndSlotToAssign = new DayAndSlotToAssign(dayInAction, isMorning(timeSlotInAction) ? timeSlotInAction : null, isAfternoon(timeSlotInAction) ? timeSlotInAction : null, assignmentInAction);
        let existingDayAndSlotToAssign = this.dayAndSlotToAssign.find( (slot)  =>  slot.equals(dayAndSlotToAssign));

        console.log("existingDayAndSlotToAssign:"+JSON.stringify(existingDayAndSlotToAssign))

        if(existingDayAndSlotToAssign){
            //revert the last one
            let previousDayAndSlotThatWasAssigned = this.dayAndSlotToAssign[this.dayAndSlotToAssign.length-1];
            if(previousDayAndSlotThatWasAssigned){
                previousDayAndSlotThatWasAssigned.assignmentInAction.availabilityType = AvailabilityType.AVAILABLE;
                previousDayAndSlotThatWasAssigned.assignmentInAction.pod = undefined;
            }
            this.dayAndSlotToAssign.pop();
            //console.log("dayAndSlotToAssign after pop:"+JSON.stringify(this.dayAndSlotToAssign))
        }else if (assignmentInAction.availabilityType === AvailabilityType.AVAILABLE) {

            //if(!existingDayAndSlotToAssign){
                // add
                //We allow to book only AVAILABLE slots otherwise we would allow overriding someone else's bookings which we do not want.
                assignmentInAction.availabilityType = AvailabilityType.POD_ASSIGNMENT;
                assignmentInAction.pod = this.selectedPodToAssign;

                if (this.usersToAssign.indexOf(userInAction.entityId.uuid) === -1) {
                    //this.podAssignmentCreateRequestUsers.push(this.podAssignmentCreateRequestTempStart.userInAction.uuid);
                    this.usersToAssign.push(userInAction.entityId.uuid);

                    //if we have more than 1 users and more than 1 dayAndSlotToAssign meaning we need to mark all AVAILABLE slots to POD_ASSIGNMENT for this duration
                    //if()
                }

                this.dayAndSlotToAssign.push(dayAndSlotToAssign);
                //console.log("assignmentsToAssign after push:"+JSON.stringify(this.assignmentsToAssign))
            //}

        }
    }

    private preparePodAssignmentCreateRequest() {
        if (this.selectedPodToAssign && this.startDayToAssign && this.startDayToAssign.isDataValid && this.endDayToAssign && this.endDayToAssign.isDataValid) {

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
                this.usersToAssign,
                this.startDayToAssign.dayInAction,
                this.startDayToAssign.timeSlotInAction,
                this.endDayToAssign.dayInAction,
                this.endDayToAssign.timeSlotInAction);

            //this.podAssignmentDialogEl().nativeElement.showModal();
            this.destroyPodAllocationCreateRequest();

            //console.log("#####################podAssignmentCreateRequest#####################" + JSON.stringify(podAssignmentCreateRequest));
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
        if (this.startDayToAssign) {
            //destroy only when we are inside booking mode.
            //console.log("destroyPodAllocationCreateRequest");
            //rest all class level variables after request is sent.
            this.startDayToAssign = undefined;
            this.endDayToAssign = undefined;
            this.usersToAssign = [];
            //this.podAssignmentCreateRequestDaysTemp = [];
            this.userAssignments = [];
            this.dayAndSlotToAssign = [];
            this.findMyPodAssignments();
        }

    }


}
