import {AfterViewInit, Component, ElementRef, OnInit, viewChild} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {SchedulerSettingsDto} from "./models/settings/scheduler.settings.model";
import {PodDto} from "./models/pod/pod.model";
import {MonthHeaderDto} from "./models/header/month-header.model";
import {DayHeaderDto} from "./models/header/day-header.model";
import {WeekHeaderDto} from "./models/header/week-header.model";
import {AvailabilityDto} from "./models/availability/availability.model";
import {UserDto} from "./models/user/user.model";
import {AVAILABILITY_TEST_DATA} from "./testdata/availability/availability.test-data";
import {USER_TEST_DATA} from "./testdata/user/user.test-data";
import {AvailabilityType} from "./models/availability/availability.enum";
import {DateUtils} from '../shared/utils/DateUtils';
import {USER_PODS_TEST_DATA} from "./testdata/user/user-pods.test-data";
import {UserPodsDto} from "./models/user/user-pods.model";
import {UserPodWatchersDto} from "./models/user/user-pod-watchers.model";
import {USER_POD_WATCHERS_TEST_DATA} from "./testdata/user/user-pod-watchers.test-data";
import {POD_ASSIGNMENT_VIEW_TEST_DATA} from "./testdata/scheduler/pod-assignment-view.test-data";
import {PodAssignmentViewDto} from "./models/pod-view/pod-view.model";
import {PodAssignmentWrapperDto} from "./models/pod-assignment/pod-assignment-wrapper.model";
import {AssignmentDto} from "./models/pod-assignment/assignment.model";
import {PodAssignmentDto} from "./models/pod-assignment/pod-assignment.model";
import {TimeSlot} from "./models/pod-assignment/time-slot.enum";
import {POD_DETAILS_TEST_DATA} from "./testdata/pod/pod-details.test-data";
import {PodDetailsDto} from "./models/pod/pod-details.model";
import {POD_TEST_DATA} from "./testdata/pod/pod.test-data";
import {
    PodAssignmentCreateRequestDto,
    PodAssignmentCreateRequestTemp
} from "./models/pod-assignment-request/pod-assignment-create-request.model";


@Component({
    selector: 'app-scheduler',
    standalone: true,
    imports: [
        FormsModule,
        DatePipe
    ],
    templateUrl: './scheduler.component.html',
    styleUrl: './scheduler.component.scss'
})
export class SchedulerComponent implements OnInit, AfterViewInit {

    schedulerSettings: SchedulerSettingsDto;

    monthHeaders: MonthHeaderDto[] = [];

    dayHeaders: DayHeaderDto[] = [];

    weekHeaders: WeekHeaderDto[] = [];

    protected readonly availability: AvailabilityDto[] = AVAILABILITY_TEST_DATA;

    protected readonly users: UserDto[] = USER_TEST_DATA;

    protected readonly userPods: UserPodsDto[] = USER_PODS_TEST_DATA;
    protected readonly userPodWatchers: UserPodWatchersDto[] = USER_POD_WATCHERS_TEST_DATA;

    protected readonly pods: PodDto[] = POD_TEST_DATA;
    protected readonly podDetails: PodDetailsDto[] = POD_DETAILS_TEST_DATA;

    //protected readonly schedulerViews: PodViewDto[] = SCHEDULER_VIEW_TEST_DATA;
    protected readonly podAssignmentViewDto: PodAssignmentViewDto = POD_ASSIGNMENT_VIEW_TEST_DATA;

    currentPodToView: PodDto = POD_TEST_DATA[0]; // TODO Default - on logon - fetch all pods of logged-in user and select first

    selectedPodToAssign?: PodDto = POD_TEST_DATA[0]; //TODO Default - This is the pod the user will select from legend to book

    private podAssignmentDialogEl = viewChild.required<ElementRef<HTMLDialogElement>>('bookingDialog');

    //podAssignmentCreateRequest: PodAssignmentToSave | undefined;
    podAssignmentCreateRequestTempStart: PodAssignmentCreateRequestTemp | undefined;
    podAssignmentCreateRequestTempEnd: PodAssignmentCreateRequestTemp | undefined;
    podAssignmentCreateRequest: PodAssignmentCreateRequestDto |undefined;

    podAssignmentCreateRequestUsers: string[] = [];

    isWeekEnd(date: string): boolean {
        /*let date = this.datePipe.t(date, 'DD.MM.YYYY');
        let day = new Date(date, '').getDay();*/
        let day = new Date(date).getDay();
        //console.log(date);
        //console.log(day);
        return (day === 0 || day === 6)
    }

    isWeekEnd2(day: string | null): boolean {
        console.log(day);
        return true;
        /*return (day === 0 || day === 6)*/
    }

    constructor(private datePipe: DatePipe) {
        this.schedulerSettings = SchedulerSettingsDto.newInstance(new Date().getFullYear(), new Date().getMonth(), 3);

        this.populateHeaders();

        this.populateSchedulerTestData();
    }

    /**
     * Workaround: only temporary solution. In reality all this must come from backend.
     * Mark remaining capacity as available or public holiday
     * @private
     */
    private populateSchedulerTestData() {

        this.dayHeaders.forEach((dayHeaderDto: DayHeaderDto) => {
            /*this.podMemberCapacities.forEach( (podMemberCapacityDto: PodMemberCapacityDto) => {
              let headerDateAsStr = this.datePipe.transform(dayHeaderDto.day, AppConstants.DATE_FORMAT);
              let find = podMemberCapacityDto.podAssignments?.find(capacityDto => capacityDto.day === headerDateAsStr);
            });*/
            this.podAssignmentViewDto.podAssignmentWrappers.forEach((podAssignmentWrapper: PodAssignmentWrapperDto) => {
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

    ngOnInit(): void {
    }


    protected readonly JSON = JSON;

    onSubmit() {
        //Recalculate the SchedulerDto
        this.schedulerSettings = SchedulerSettingsDto.newInstance(this.schedulerSettings.yearToView, this.schedulerSettings.startMonthToView, this.schedulerSettings.noOfMonthsToView)

        this.populateHeaders();
    }

    populateHeaders() {

        //populate month header
        this.monthHeaders = [];
        for (let month = this.schedulerSettings.startMonthToView; month < this.schedulerSettings.endMonthToView; month++) {
            //this.monthsToView.push(SchedulerDto.monthNames[month]);
            let noOfDays = new Date(this.schedulerSettings.yearToView, month + 1, 0).getDate();
            let monthDto = new MonthHeaderDto(month, noOfDays);
            this.monthHeaders.push(monthDto);
        }

        //populate days header
        this.dayHeaders = [];
        this.monthHeaders.forEach((monthHeaderDto: MonthHeaderDto) => {
            let month = monthHeaderDto.month;
            var date = new Date(this.schedulerSettings.yearToView, month, 1);

            while (date.getMonth() === month) {
                this.dayHeaders.push(new DayHeaderDto(new Date(date)));
                date.setDate(date.getDate() + 1);
            }
        });

        //populate week header
        this.weekHeaders = [];
        this.dayHeaders.forEach((dayHeaderDto: DayHeaderDto) => {
            let weekNumber = parseInt("" + this.datePipe.transform(dayHeaderDto.day, 'w'));
            let weekHeaderDto = this.weekHeaders.find(week => week.weekNumber === weekNumber);
            if (weekHeaderDto) {
                //We already exists
                weekHeaderDto.increaseNoOfDaysByOne();
            } else {
                weekHeaderDto = new WeekHeaderDto(weekNumber);
                weekHeaderDto.increaseNoOfDaysByOne();
                this.weekHeaders.push(weekHeaderDto)
            }
        });

    }


    protected readonly DateUtils = DateUtils;

    onDragStart($event: MouseEvent,
                clickedPodAssignmentWrapper: PodAssignmentWrapperDto,
                userInAction: UserDto,
                timeSlotInAction: TimeSlot,
                assignmentInAction: AssignmentDto,
                podInAction?: PodDto,
                dayAsStrInAction?: string | null,
                dayInAction?: Date | null) {
        console.log("onDragStart...");

        //we do not need this once we integrate backend service
        if (!dayInAction && dayAsStrInAction) {
            dayInAction = DateUtils.parseISODate(dayAsStrInAction);
        }

        this.allocateUsersToCurrentPod(userInAction, dayAsStrInAction, dayInAction, timeSlotInAction, assignmentInAction);

        /*clickedPodAssignmentWrapper.podAssignments.forEach( (podAssignment: PodAssignmentDto) => {
            podAssignment.morning.pod = this.selectedPodToAssign;
            podAssignment.afternoon.pod = this.selectedPodToAssign;
        });*/
        // @ts-ignore : dayInAction would never be null
        this.podAssignmentCreateRequestTempStart = new PodAssignmentCreateRequestTemp(userInAction, dayInAction, timeSlotInAction);
    }

    onWhileDragging($event: MouseEvent,
                    podAssignmentWrapper: PodAssignmentWrapperDto,
                    userInAction: UserDto,
                    timeSlotInAction: TimeSlot,
                    assignmentInAction: AssignmentDto,
                    pod: PodDto | undefined,
                    dayAsStrInAction: string | null | undefined,
                    dayInAction: Date | null | undefined) {
        if(this.podAssignmentCreateRequestTempStart && !this.podAssignmentCreateRequestTempEnd){
            console.log("whileDragging...");
            console.log("userInAction:" + JSON.stringify(userInAction));
            console.log("dayAsStrInAction:" + JSON.stringify(dayAsStrInAction));
            console.log("dayInAction:" + JSON.stringify(dayInAction));
            console.log("timeSlotInAction:" + JSON.stringify(timeSlotInAction));
            console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));
            this.allocateUsersToCurrentPod(userInAction, dayAsStrInAction, dayInAction, timeSlotInAction, assignmentInAction);
        }


    }

    onDragEnd($event: MouseEvent,
              clickedPodAssignmentWrapper: PodAssignmentWrapperDto,
              userInAction: UserDto,
              timeSlotInAction: TimeSlot,
              assignmentInAction: AssignmentDto,
              podInAction?: PodDto,
              dayAsStrInAction?: string | null,
              dayInAction?: Date | null) {
        console.log("onDragEnd...");

        //we do not need this once we integrate backend service
        if (!dayInAction && dayAsStrInAction) {
            dayInAction = DateUtils.parseISODate(dayAsStrInAction);
        }


        this.allocateUsersToCurrentPod(userInAction, dayAsStrInAction, dayInAction, timeSlotInAction, assignmentInAction);

        // @ts-ignore : dayInAction would never be null
        this.podAssignmentCreateRequestTempEnd = new PodAssignmentCreateRequestTemp(userInAction, dayInAction, timeSlotInAction);

        this.preparePodAssignmentCreateRequest();
    }

    private allocateUsersToCurrentPod(userInAction: UserDto, dayAsStrInAction: string | null | undefined, dayInAction: Date | null | undefined, timeSlotInAction: TimeSlot, assignmentInAction: AssignmentDto) {
        console.log("userInAction:" + JSON.stringify(userInAction));
        console.log("dayAsStrInAction:" + JSON.stringify(dayAsStrInAction));
        console.log("dayInAction:" + JSON.stringify(dayInAction));
        console.log("timeSlotInAction:" + JSON.stringify(timeSlotInAction));
        console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));
        console.log("assignmentInAction:" + JSON.stringify(assignmentInAction));


        if (assignmentInAction.availabilityType === AvailabilityType.AVAILABLE) {
            //We allow to book only AVAILABLE slots otherwise we would allow overriding someone else's bookings which we do not want,.
            assignmentInAction.availabilityType = AvailabilityType.POD_ASSIGNMENT;
            assignmentInAction.pod = this.selectedPodToAssign;
        }
    }

    private preparePodAssignmentCreateRequest() {
        if (this.selectedPodToAssign && this.podAssignmentCreateRequestTempStart && this.podAssignmentCreateRequestTempStart.isDataValid && this.podAssignmentCreateRequestTempEnd && this.podAssignmentCreateRequestTempEnd.isDataValid) {

            this.podAssignmentCreateRequestUsers.push(this.podAssignmentCreateRequestTempStart.userInAction.uuid);
            //Is start and end user different, if yes then we need to select all in-between users
            if (this.podAssignmentCreateRequestTempStart.userInAction.gpin !== this.podAssignmentCreateRequestTempEnd.userInAction.gpin) {
                for (let podAssignmentWrapper of this.podAssignmentViewDto.podAssignmentWrappers) {
                    this.podAssignmentCreateRequestUsers.push(podAssignmentWrapper.user.uuid);
                    if (podAssignmentWrapper.user.gpin === this.podAssignmentCreateRequestTempEnd?.userInAction.gpin) {
                        break;
                    }
                }
            }
            //usersToAssignToAPodAsUuids = [...new Set(usersToAssignToAPod.map( (u) => u.uuid))]//remove duplicates;
            this.podAssignmentCreateRequestUsers = [...new Set(this.podAssignmentCreateRequestUsers)]//remove duplicates;
            //console.log("usersToAssignToAPod: "+ JSON.stringify(usersToAssignToAPod));

            let podAssignmentToSaveTempStartCloned = {...this.podAssignmentCreateRequestTempStart} //lets clone because we are resetting this at the end
            let podAssignmentToSaveTempEndCloned = {...this.podAssignmentCreateRequestTempEnd}
            let podAssignmentCreateRequestUsersCloned = {...this.podAssignmentCreateRequestUsers}
            this.podAssignmentCreateRequest = new PodAssignmentCreateRequestDto(
                podAssignmentCreateRequestUsersCloned,
                this.selectedPodToAssign.uuid,
                podAssignmentToSaveTempStartCloned.dayInAction,
                podAssignmentToSaveTempStartCloned.timeSlotInAction,
                podAssignmentToSaveTempEndCloned.dayInAction,
                podAssignmentToSaveTempEndCloned.timeSlotInAction);

            //rest all class level variables after request is sent.
            this.podAssignmentCreateRequestTempStart = undefined;
            this.podAssignmentCreateRequestTempEnd = undefined;
            this.podAssignmentCreateRequestUsers = [];
            //this.podAssignmentDialogEl().nativeElement.showModal();

            console.log("#####################podAssignmentCreateRequest#####################" + JSON.stringify(this.podAssignmentCreateRequest));
        }
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


}
