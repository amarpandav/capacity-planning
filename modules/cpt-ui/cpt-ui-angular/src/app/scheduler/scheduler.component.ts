import {AfterViewInit, Component, DestroyRef, OnInit} from '@angular/core';
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
import {TimeSlot} from "./models/pod-assignment/time-slot.enum";
import {POD_DETAILS_TEST_DATA} from "../../testdata/pod/pod-details.test-data";
import {PodDetailsDto} from "./models/pod/pod-details.model";
import {POD_TEST_DATA} from "../../testdata/pod/pod.test-data";
import {
    FlexDayToRepaint,
    PodAssignmentCreateRequestDto,
    StartOrEndDayToAssign
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
import {PodAssignmentDto} from "./models/pod-assignment/pod-assignment.model";
import {getPodMemberRoleValue, PodMemberRole} from "./models/pod/pom-member-role.enum";
import {UserSkillsComponent} from "../user-skills/user-skills.component";


@Component({
    selector: 'app-scheduler',
    standalone: true,
    imports: [
        FormsModule,
        DatePipe,
        UserComponent,
        UserListComponent,
        UserViewingBoxComponent,
        SchedulerSettingsComponent,
        UserSkillsComponent
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

    mySelectedPod?: PodDto;

    selectedPodToAssign?: PodDto;

    selectedAvailabilityType?: AvailabilityType;


    //private podAssignmentDialogElemRef = viewChild.required<ElementRef<HTMLDialogElement>>('bookingDialog');
    //private userSkillsDialogElemRef = viewChild.required<ElementRef<HTMLDialogElement>>('userSkillsDialog');
    //private userSkillsComponentElemRef = viewChild.required<ComponentRef<UserSkillsComponent>>('userSkillsComponent');

        //podAssignmentCreateRequest: PodAssignmentToSave | undefined;
    //podAssignmentCreateRequest: PodAssignmentCreateRequestDto | undefined;

    //podAssignmentCreateRequestUsers: Set<string>  = new Set();
    usersToAssign: string[] = [];
    flexDaysToRepaint: FlexDayToRepaint[] = [];
    startDayToAssign: StartOrEndDayToAssign | undefined;
    endDayToAssign: StartOrEndDayToAssign | undefined;
    //podAssignmentCreateRequestDays: Date[] = [];
    //podAssignmentCreateRequestDaysTemp: PodAssignmentCreateRequestDayTemp[] = [];
    assignmentToAssign: AssignmentDto[] = [];

    selectedUser?: UserDto;
    selectedUserPodMemberRole?: PodMemberRole;
    relatedPodMemberRoles: PodMemberRole[] = [];
    //@Output() selectUserAsOutputEvent = new EventEmitter<UserDto>();
    //selectedUserPodMemberRoleAsOutputEvent = output<PodMemberRole>(); //
    selectedPodMember?: UserDto;

    constructor(private datePipe: DatePipe,
                private destroyRef: DestroyRef,
                private schedulerService: SchedulerService,
                private schedulerHeaderService: SchedulerHeaderService) {
        this.schedulerSettings = SchedulerSettingsDto.newDefaultInstance();
        this.schedulerHeader = this.schedulerHeaderService.findSchedulerHeader(this.schedulerSettings);
    }

    onSelectedUserInput(selectedUser: UserDto) {
        // console.log("I am (SchedulerComponent) consuming emitted user as an Object: " + JSON.stringify(selectedUser));
        this.selectedUser = selectedUser;
    }

    onSelectedPodToAssignInput(podDto: PodDto |undefined) {
        // console.log("I am (SchedulerComponent) consuming emitted selected pod to assign as an Object: " + JSON.stringify(podDto));
        this.selectedPodToAssign = podDto;
    }

    onSelectedAvailabilityTypeInput(selectedAvailabilityType: AvailabilityType | undefined) {
        this.selectedAvailabilityType = selectedAvailabilityType;
    }


    ngOnInit(): void {
        //this.isLoadingPlaces.set(true);
        //this.findMyPodAssignments();
    }

    private findMyPodAssignments() {
        if (this.mySelectedPod) {
            this.relatedPodMemberRoles = [];
            this.userAssignments = undefined;
            const subscription1 = this.schedulerService.findMyPodAssignments(this.mySelectedPod.entityId, this.schedulerSettings)
                .subscribe({
                        next: (userAssignments) => {
                            //console.log(podAssignmentView);
                            this.userAssignments = userAssignments;
                            userAssignments.forEach((ua: UserAssignmentDto) => {
                                if(this.selectedUser?.entityId.uuid === ua.user.entityId.uuid){
                                    this.selectedUserPodMemberRole = ua.podMemberRole;
                                    //this.selectedUserPodMemberRoleAsOutputEvent.emit(ua.podMemberRole);
                                    if(this.relatedPodMemberRoles?.indexOf(ua.podMemberRole) < 0){
                                        this.relatedPodMemberRoles.push(ua.podMemberRole);
                                    }
                                }
                                ua.podAssignments.forEach((pa: PodAssignmentDto) => {
                                    if(pa.morning.availabilityType === AvailabilityType.AVAILABLE) {
                                        pa.morning.persisted = false;
                                    }else {
                                        pa.morning.persisted = true;
                                    }
                                    if(pa.afternoon.availabilityType === AvailabilityType.AVAILABLE) {
                                        pa.afternoon.persisted = false;
                                    }else {
                                        pa.afternoon.persisted = true;
                                    }
                                })
                            })
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

    onSchedulerSettingsInput(schedulerSettings: SchedulerSettingsDto) {
        this.schedulerSettings = schedulerSettings;

        //Recalculate the SchedulerDto
        this.schedulerHeader = this.schedulerHeaderService.findSchedulerHeader(this.schedulerSettings);
        this.findMyPodAssignments();
    }

    onSelectedMyPodInput(mySelectedPod: PodDto) {
        //console.log("onSelectedMyPodEntityIdInput received: "+ JSON.stringify(mySelectedPod));
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
        if (this.selectedPodToAssign) {
            //console.log("onDragStart...");

            // @ts-ignore : dayInAction would never be null
            this.startDayToAssign = new StartOrEndDayToAssign(userInAction, dayInAction, timeSlotInAction);

            // @ts-ignore : dayInAction would never be null
            this.addAssignment(userInAction, dayInAction, timeSlotInAction, assignmentInAction);

            /*clickedUserAssignment.podAssignments.forEach( (podAssignment: PodAssignmentDto) => {
                podAssignment.morning.pod = this.selectedPodToAssign;
                podAssignment.afternoon.pod = this.selectedPodToAssign;
            });*/

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
        if (this.selectedPodToAssign) {
            //console.log("onDragEnd...");

            // @ts-ignore : dayInAction would never be null
            this.endDayToAssign = new StartOrEndDayToAssign(userInAction, dayInAction, timeSlotInAction);

            // @ts-ignore : dayInAction would never be null
            this.addAssignment(userInAction, dayInAction, timeSlotInAction, assignmentInAction);

            this.preparePodAssignmentCreateRequest();
        }

    }

    private addAssignment(userInAction: UserDto, dayInAction: Date, timeSlotInAction: TimeSlot, assignmentInAction: AssignmentDto) {
        console.log("dayInAction as object " + dayInAction);
        console.log("dayInAction as string " + JSON.stringify(dayInAction));
        /* console.log("userInAction:" + JSON.stringify(userInAction));
         console.log("dayInAction:" + JSON.stringify(dayInAction));
         console.log("timeSlotInAction:" + JSON.stringify(timeSlotInAction));
         console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));*/

        this.assignmentToAssign.push(assignmentInAction);

        if (this.usersToAssign.indexOf(userInAction.entityId.uuid) === -1) {
            //this.podAssignmentCreateRequestUsers.push(this.podAssignmentCreateRequestTempStart.userInAction.uuid);
            this.usersToAssign.push(userInAction.entityId.uuid);
        }

        if (assignmentInAction.availabilityType === AvailabilityType.AVAILABLE) {
            let flexDayToRepaint = new FlexDayToRepaint(dayInAction, timeSlotInAction/*, assignmentInAction*/);
            //console.log("testing with Lukasz: "+dayInAction.get)
            let existingFlexDayToRepaint = this.flexDaysToRepaint.find((slot) => slot.equals(flexDayToRepaint))
            if (!existingFlexDayToRepaint) {
                let flexDayToRepaint = new FlexDayToRepaint(dayInAction, timeSlotInAction/*, assignmentInAction*/);
                //console.log("not existing: "+JSON.stringify(flexDayToRepaint));
                this.flexDaysToRepaint.push(flexDayToRepaint);
            }
        }

        /*let existingFlexDayToRepaint = this.flexDaysToRepaint.find((slot) => slot.equals(flexDayToRepaint));
        if(!existingFlexDayToRepaint) {
            this.flexDaysToRepaint.push(flexDayToRepaint);
        }*/

        //console.log("BEFORE: this.usersToAssign.length: " + this.usersToAssign.length +"; "+JSON.stringify(this.usersToAssign));
        console.log("BEFORE: this.flexDaysToRepaint.length: " + this.flexDaysToRepaint.length + "; " + JSON.stringify(this.flexDaysToRepaint));

        //1. rest assignmentInAction for all usersToAssign
        this.flexDaysToRepaint.forEach((fd: FlexDayToRepaint) => {
            //fd.assignmentInAction.availabilityType = AvailabilityType.AVAILABLE;
            //fd.assignmentInAction.pod = undefined;
            this.usersToAssign.forEach((uuid: string) => {
                let ua = this.userAssignments?.find((ua: UserAssignmentDto) => ua.user.entityId.uuid === uuid);
                if (ua) {
                    let pa = ua.podAssignments.find((pa) => {
                        return pa.day.getTime() === fd.day.getTime()
                    });
                    if (pa && fd.timeSlotInAction === TimeSlot.MORNING && !pa.morning.persisted/*pa.morning.availabilityType != AvailabilityType.POD_ASSIGNMENT*//*&& (!pa.morning.pod || !pa.morning.persisted)*/) {
                        pa.morning.availabilityType = AvailabilityType.AVAILABLE;
                        pa.morning.pod = undefined;
                        //pa.morning.persistedPod = false;
                    } else if (pa && fd.timeSlotInAction === TimeSlot.AFTERNOON && !pa.afternoon.persisted/*pa.afternoon.availabilityType != AvailabilityType.POD_ASSIGNMENT*//*&& (!pa.afternoon.pod || !pa.afternoon.persisted)*/) {
                        pa.afternoon.availabilityType = AvailabilityType.AVAILABLE;
                        pa.afternoon.pod = undefined;
                        //pa.afternoon.persistedPod = false;
                    }
                }
            });
        });

        //2. remove for all users
        let oldUsersToAssign = [...this.usersToAssign];

        //this.usersToAssign.slice(1);
        this.usersToAssign = [];
        this.flexDaysToRepaint = [];

        //3. re-add all users until current if current > start else or start > current
        //3.1 if current and start is same
        if (userInAction.entityId.uuid === this.startDayToAssign?.userInAction.entityId.uuid) {
            this.usersToAssign.push(userInAction.entityId.uuid);
        } else {
            //3.2 top -> bottom : start (small) to current (bigger) - user is dragging the mouse vertically downwards
            let startAdding = false;
            for (let uuid of oldUsersToAssign) {
                //is it start
                if (uuid === this.startDayToAssign?.userInAction.entityId.uuid) {
                    //start
                    startAdding = true;
                }
                if (startAdding && uuid !== userInAction.entityId.uuid) {
                    //middle
                    this.usersToAssign.push(uuid);
                }
                //is it current?
                if (startAdding && uuid === userInAction.entityId.uuid) {
                    //end
                    startAdding = false;
                    this.usersToAssign.push(uuid);
                    break;
                }
            }

            if (this.usersToAssign.length === 0) {
                //Still empty
                //3.3 bottom -> top: current (small) to start (bigger) - user is dragging the mouse vertically upwards
                startAdding = false;
                for (let uuid of oldUsersToAssign) {
                    //is it current
                    if (uuid === userInAction.entityId.uuid) {
                        //start
                        startAdding = true;
                    }
                    if (startAdding && uuid !== userInAction.entityId.uuid) {
                        //middle
                        this.usersToAssign.push(uuid);
                    }
                    //is it start?
                    if (startAdding && uuid === this.startDayToAssign?.userInAction.entityId.uuid) {
                        startAdding = false;
                        this.usersToAssign.push(uuid);
                        break;
                    }
                }
            }
        }
        //console.log("AFTER: this.usersToAssign.length: " + this.usersToAssign.length +"; "+JSON.stringify(this.usersToAssign));


        // @ts-ignore
        let startDate = this.startDayToAssign?.dayInAction;
        let startTimeSlot = this.startDayToAssign?.timeSlotInAction;
        //let startAsignment = this.startDayToAssign.
        let currentDate = dayInAction;
        let currentTimeSlot = timeSlotInAction;
        //4. re-paint cells for usersToAssign start to end

        if (startDate && currentDate && startTimeSlot && currentTimeSlot) {
            if (startDate.getTime() === currentDate.getTime() && startTimeSlot === currentTimeSlot) {
                //4.1 if current and start is same
                console.log("start and current are same");
                let flexDayToRepaint = new FlexDayToRepaint(dayInAction, timeSlotInAction/*, assignmentInAction*/);
                this.flexDaysToRepaint.push(flexDayToRepaint);
                //console.log("1");
            } else {
                // @ts-ignore
                if (startDate.getTime() === currentDate.getTime()) {
                    //4.2 left <-> right but on same day
                    console.log("dragging left or right but same day");

                    //add start date
                    let flexDayToRepaint = new FlexDayToRepaint(startDate, startTimeSlot/*, assignmentInAction*/);
                    this.flexDaysToRepaint.push(flexDayToRepaint);

                    flexDayToRepaint = new FlexDayToRepaint(currentDate, currentTimeSlot/*, assignmentInAction*/);
                    this.flexDaysToRepaint.push(flexDayToRepaint);
                } else if (startDate.getTime() < currentDate.getTime()) {
                    //4.2 left -> right: start (small) to current (bigger) - user is dragging mouse to right hand side without changing the pod member row
                    console.log("dragging right");

                    //add start date
                    let flexDayToRepaint = new FlexDayToRepaint(startDate, startTimeSlot);
                    this.flexDaysToRepaint.push(flexDayToRepaint);
                    if (startTimeSlot === TimeSlot.MORNING) {
                        flexDayToRepaint = new FlexDayToRepaint(startDate, TimeSlot.AFTERNOON);
                        this.flexDaysToRepaint.push(flexDayToRepaint);
                    }
                    console.log("flexDaysToRepaint1: " + JSON.stringify(this.flexDaysToRepaint));

                    // @ts-ignore
                    let loop = startDate;
                    // @ts-ignoreø
                    let end = currentDate;
                    //console.log("loop:"+JSON.stringify(loop) + ";"+startTimeSlot);
                    //console.log("end:"+JSON.stringify(end) + ";"+currentTimeSlot);

                    while (loop < end) {
                        //go to next day
                        loop = DateUtils.nextDay(loop);

                        flexDayToRepaint = new FlexDayToRepaint(loop, TimeSlot.MORNING);
                        this.flexDaysToRepaint.push(flexDayToRepaint);
                        // console.log("flexDaysToRepaint2: "+JSON.stringify(this.flexDaysToRepaint));

                        if (loop.getTime() !== end.getTime()) {
                            //middle somewhere between start and end hence blindly we can create afternoon
                            flexDayToRepaint = new FlexDayToRepaint(loop, TimeSlot.AFTERNOON);
                            this.flexDaysToRepaint.push(flexDayToRepaint);
                        }

                        console.log("flexDaysToRepaint2: " + JSON.stringify(this.flexDaysToRepaint));

                        if (loop.getTime() == end.getTime() && currentTimeSlot === TimeSlot.AFTERNOON) {
                            //We reached end, create afternoon only when its end Time Slot
                            flexDayToRepaint = new FlexDayToRepaint(loop, TimeSlot.AFTERNOON);
                            this.flexDaysToRepaint.push(flexDayToRepaint);
                        }
                    }

                } else {
                    //4.3 right -> left: current (small) to start (bigger) - user is dragging mouse to left hand side without changing the pod member row
                    console.log("dragging left");

                    //add start date
                    let flexDayToRepaint = new FlexDayToRepaint(startDate, startTimeSlot);
                    this.flexDaysToRepaint.push(flexDayToRepaint);
                    if (startTimeSlot === TimeSlot.AFTERNOON) {
                        flexDayToRepaint = new FlexDayToRepaint(startDate, TimeSlot.MORNING);
                        this.flexDaysToRepaint.push(flexDayToRepaint);
                    }

                    // @ts-ignore
                    let loop = DateUtils.cloneDay(startDate)
                    // @ts-ignore
                    let end = DateUtils.cloneDay(currentDate);

                    while (loop > end) {
                        //go to prev day
                        loop = DateUtils.prevDay(loop);

                        flexDayToRepaint = new FlexDayToRepaint(loop, TimeSlot.AFTERNOON);
                        this.flexDaysToRepaint.push(flexDayToRepaint);

                        if (loop.getTime() !== end.getTime()) {
                            //middle somewhere between start and end hence blindly we can create afternoon
                            flexDayToRepaint = new FlexDayToRepaint(loop, TimeSlot.MORNING);
                            this.flexDaysToRepaint.push(flexDayToRepaint);
                        }

                        if (loop.getTime() == end.getTime() && currentTimeSlot === TimeSlot.MORNING) {
                            //We reached end, create morning only when its end Time Slot
                            flexDayToRepaint = new FlexDayToRepaint(loop, TimeSlot.MORNING);
                            this.flexDaysToRepaint.push(flexDayToRepaint);
                        }
                    }
                }
            }
        }

        console.log("AFTER: this.flexDaysToRepaint.length: " + this.flexDaysToRepaint.length + "; " + JSON.stringify(this.flexDaysToRepaint));
        this.flexDaysToRepaint.forEach((fd: FlexDayToRepaint) => {
            //fd.assignmentInAction.availabilityType = AvailabilityType.AVAILABLE;
            //fd.assignmentInAction.pod = undefined;
            this.usersToAssign.forEach((uuid: string) => {
                let ua = this.userAssignments?.find((ua: UserAssignmentDto) => ua.user.entityId.uuid === uuid);
                if (ua) {
                    let pa = ua.podAssignments.find((pa) => {
                        /*console.log(pa.day.toString());
                        console.log("fd is:");
                        console.log(DateUtils.formatToISODate(fd.day));*/
                        return pa.day.getTime() === fd.day.getTime()
                    });
                    console.log("pa: " + JSON.stringify(pa));
                    if (pa && fd.timeSlotInAction === TimeSlot.MORNING) {
                        //console.log("MORNING: user: "+ua.user.name+"; day:"+pa.day+", availability:"+pa.morning.availabilityType);
                        if (pa.morning.availabilityType === AvailabilityType.AVAILABLE) {
                            //console.log("assign pod inside 199");
                            pa.morning.availabilityType = AvailabilityType.POD_ASSIGNMENT;
                            pa.morning.pod = this.selectedPodToAssign;
                        } else {
                            //console.log("Do not assign pod, 199");
                        }
                    } else if (pa && fd.timeSlotInAction === TimeSlot.AFTERNOON) {
                        //console.log("AFTERNOON: user: "+ua.user.name+"; day:"+pa.day+", availability:"+pa.afternoon.availabilityType);
                        if (pa.afternoon.availabilityType === AvailabilityType.AVAILABLE) {
                            //console.log("assign pod inside 200");
                            pa.afternoon.availabilityType = AvailabilityType.POD_ASSIGNMENT;
                            pa.afternoon.pod = this.selectedPodToAssign;
                        } else {
                            //console.log("Do not assign pod, 200");
                        }

                    }
                }
            });
        });
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
            let podAssignmentCreateRequest;
            if (this.startDayToAssign.dayInAction > this.endDayToAssign.dayInAction) {
                //right to left dragging
                podAssignmentCreateRequest = new PodAssignmentCreateRequestDto(
                    this.selectedPodToAssign.entityId.uuid,
                    this.usersToAssign,
                    this.endDayToAssign.dayInAction,
                    this.endDayToAssign.timeSlotInAction,
                    this.startDayToAssign.dayInAction,
                    this.startDayToAssign.timeSlotInAction);

            } else {
                //left to right dragging
                podAssignmentCreateRequest = new PodAssignmentCreateRequestDto(
                    this.selectedPodToAssign.entityId.uuid,
                    this.usersToAssign,
                    this.startDayToAssign.dayInAction,
                    this.startDayToAssign.timeSlotInAction,
                    this.endDayToAssign.dayInAction,
                    this.endDayToAssign.timeSlotInAction);

            }


            //this.podAssignmentDialogElemRef().nativeElement.showModal();
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

    /*onBookingDialogCancel() {
        this.podAssignmentDialogElemRef().nativeElement.close();
    }*/

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
            this.flexDaysToRepaint = [];
            this.findMyPodAssignments();
        }

    }


    protected readonly getPodMemberRoleValue = getPodMemberRoleValue;

    onSelectedPodMember(user: UserDto) {
        this.selectedPodMember = user;

        //console.log(this.userSkillsDialogElemRef());
        //this.userSkillsDialogElemRef().nativeElement.showModal();

        //console.log(this.userSkillsComponentElemRef().location.nativeElement);
        //this.userSkillsComponentElemRef().instance.showModal();
    }

    onCloseUserSkillsDialogInput($event: UserDto | undefined) {
        this.selectedPodMember = undefined;
    }
}
