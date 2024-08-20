import { Component, OnInit } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { DatePipe } from "@angular/common";
import { SchedulerSettingsDto } from "./models/settings/scheduler.settings.model";
import { PodWatcherDto } from "./models/pod/pod.watcher.model";
import { PodDto } from "./models/pod/pod.model";
import { MonthHeaderDto } from "./models/header/month-header.model";
import { DayHeaderDto } from "./models/header/day-header.model";
import { WeekHeaderDto } from "./models/header/week-header.model";
import { PodMemberDto } from "./models/pod/pod-member.model";
import { AvailabilityDto } from "./models/availability/availability.model";
import { UserAvailableCapacityDto } from "./models/user/user-available-capacity.model";
import { UserDto } from "./models/user/user.model";
import { AVAILABILITY_TEST_DATA } from "./testdata/availability/availability.test-data";
import { USER_TEST_DATA } from "./testdata/user/user.test-data";
import { USER_AVAILABLE_CAPACITY_TEST_DATA } from "./testdata/user/user-available-capacity.test-data";
import { POD_MEMBER_TEST_DATA } from "./testdata/pod/pod-member.test-data";
import { POD_WATCHER_TEST_DATA } from "./testdata/pod/pod-watcher.test-data";
import { POD_TEST_DATA } from "./testdata/pod/pod.test-data";
import { SCHEDULER_VIEW_TEST_DATA } from "./testdata/scheduler/scheduler-view.test-data";
import { SchedulerViewDto } from "./models/scheduler/scheduler-view.model";
import { AvailabilityType } from "./models/availability/availability.enum";
import { UserCapacityDto } from "./models/user/user.capacity.model";
import { DateUtils } from '../shared/utils/DateUtils';

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
export class SchedulerComponent implements OnInit {

    schedulerSettings: SchedulerSettingsDto;

    monthHeaders: MonthHeaderDto[] = [];

    dayHeaders: DayHeaderDto[] = [];

    weekHeaders: WeekHeaderDto[] = [];

    protected readonly availability: AvailabilityDto[] = AVAILABILITY_TEST_DATA;

    protected readonly users: UserDto[] = USER_TEST_DATA;
    protected readonly userAvailableCapacities: UserAvailableCapacityDto[] = USER_AVAILABLE_CAPACITY_TEST_DATA;

    protected readonly podMembers: PodMemberDto[] = POD_MEMBER_TEST_DATA;
    protected readonly podWatchers: PodWatcherDto[] = POD_WATCHER_TEST_DATA;

    protected readonly pods: PodDto[] = POD_TEST_DATA;

    protected readonly schedulerViews: SchedulerViewDto[] = SCHEDULER_VIEW_TEST_DATA;

    selectedPod: PodDto = POD_TEST_DATA[0]; // TODO Default - on logon - fetch all pods of logged-in user and select first

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
              let find = podMemberCapacityDto.userCapacities?.find(capacityDto => capacityDto.day === headerDateAsStr);
            });*/
            this.schedulerViews.forEach((schedulerViewDto: SchedulerViewDto) => {
                //let userDto = schedulerViewDto.user;

                //let headerDateAsStr = "" + this.datePipe.transform(dayHeaderDto.day, AppConstants.DATE_FORMAT);
                let headerDateAsStr = DateUtils.formatToISODate(dayHeaderDto.day);
                let userCapacityDto = schedulerViewDto.userCapacities?.find(capacityDto => capacityDto.dayAsStr === headerDateAsStr);

                if (userCapacityDto) {
                    //capacity for this day do exists
                    userCapacityDto.day = DateUtils.parseISODate(userCapacityDto.dayAsStr);//This is needed because constructor is not called when we use schedulerViews: SchedulerViewDto[] = SCHEDULER_VIEW_TEST_DATA
                    //capacity for this day exists, check if its complete otherwise complete it
                    //completing it means, morning and afternoon must be filled in with either booking or availability
                    //console.log("userCapacityDto found: " + JSON.stringify(userCapacityDto));
                    /*console.log("userCapacityDto.userBookedCapacity?.morningPod: " + userCapacityDto.userBookedCapacity?.morningPod);
                    console.log("userCapacityDto.userBookedCapacity?.afternoonPod: " + userCapacityDto.userBookedCapacity?.afternoonPod);
                    console.log("userCapacityDto.userAvailableCapacity?.morningAvailability: " + userCapacityDto.userAvailableCapacity?.morningAvailability);
                    console.log("userCapacityDto.userAvailableCapacity?.afternoonAvailability: " + userCapacityDto.userAvailableCapacity?.afternoonAvailability);*/
                    if (!userCapacityDto.userBookedCapacity?.morningPod && !userCapacityDto.userAvailableCapacity?.morningAvailability) {
                        //both do not exist hence create  userAvailableCapacity as available or public holiday
                        let morningAvailabilityDto;
                        if (DateUtils.isWeekend(dayHeaderDto.day)) {
                            morningAvailabilityDto = AVAILABILITY_TEST_DATA.find(availabilityDto => availabilityDto?.availabilityType === AvailabilityType.PUBLIC_HOLIDAY);
                        } else {
                            morningAvailabilityDto = AVAILABILITY_TEST_DATA.find(availabilityDto => availabilityDto?.availabilityType === AvailabilityType.AVAILABLE);
                        }
                        userCapacityDto.userAvailableCapacity = new UserAvailableCapacityDto("userAvailableCapacityUuid-" + headerDateAsStr, headerDateAsStr, null, morningAvailabilityDto, null);
                    } else if (!userCapacityDto.userBookedCapacity?.afternoonPod && !userCapacityDto.userAvailableCapacity?.afternoonAvailability) {
                        //both do not exist hence create  userAvailableCapacity as available or public holiday
                        let afternoonAvailabilityDto
                        if (DateUtils.isWeekend(dayHeaderDto.day)) {
                            afternoonAvailabilityDto = AVAILABILITY_TEST_DATA.find(availabilityDto => availabilityDto?.availabilityType === AvailabilityType.PUBLIC_HOLIDAY);
                        } else {
                            afternoonAvailabilityDto = AVAILABILITY_TEST_DATA.find(availabilityDto => availabilityDto?.availabilityType === AvailabilityType.AVAILABLE);
                        }
                        userCapacityDto.userAvailableCapacity = new UserAvailableCapacityDto("userAvailableCapacityUuid-" + headerDateAsStr, headerDateAsStr, null, afternoonAvailabilityDto);
                    }
                } else {
                    //capacity for this day do not exist, complete it now
                    let availableFullDayAvailabilityDto
                    if (DateUtils.isWeekend(dayHeaderDto.day)) {
                        //console.log(dayHeaderDto.day +"is holiday");
                        availableFullDayAvailabilityDto = AVAILABILITY_TEST_DATA.find(availabilityDto => availabilityDto?.availabilityType === AvailabilityType.PUBLIC_HOLIDAY);
                        //console.log(JSON.stringify(availableFullDayAvailabilityDto));
                    } else {
                        availableFullDayAvailabilityDto = AVAILABILITY_TEST_DATA.find(availabilityDto => availabilityDto?.availabilityType === AvailabilityType.AVAILABLE);
                    }
                    let userAvailableCapacity = new UserAvailableCapacityDto("userAvailableCapacityUuid-" + headerDateAsStr, headerDateAsStr, null, availableFullDayAvailabilityDto, availableFullDayAvailabilityDto);

                    let userCapacityDto1 = new UserCapacityDto(headerDateAsStr, null, null, userAvailableCapacity);
                    schedulerViewDto.userCapacities?.push(userCapacityDto1)
                }

                //sort userCapacities otherwise manually added capacities would be pushed at the end of the list.
                schedulerViewDto.userCapacities?.sort((a: UserCapacityDto, b: UserCapacityDto) => {
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

    onDragStart($event: MouseEvent, user: UserDto, userCapacity: UserCapacityDto, morningPod: PodDto | null | undefined, morningAvailability: AvailabilityDto | null | undefined, afternoonPod: PodDto | null | undefined, afternoonAvailability: AvailabilityDto | null | undefined) {
        console.log("onDragStart...");
        console.log("user:"+ JSON.stringify(user));
        console.log("userCapacity:"+ JSON.stringify(userCapacity));
        console.log("morningPod:"+ JSON.stringify(morningPod));
        console.log("morningAvailability:"+ JSON.stringify(morningAvailability));
        console.log("afternoonPod:"+ JSON.stringify(afternoonPod));
        console.log("afternoonAvailability:"+ JSON.stringify(afternoonAvailability));

    }

    onDragEnd($event: MouseEvent, user: UserDto, userCapacity: UserCapacityDto, morningPod: PodDto | null | undefined, morningAvailability: AvailabilityDto | null | undefined, afternoonPod: PodDto | null | undefined, afternoonAvailability: AvailabilityDto | null | undefined) {
        console.log("onDragEnd...");
        console.log("user:"+ JSON.stringify(user));
        console.log("userCapacity:"+ JSON.stringify(userCapacity));
        console.log("morningPod:"+ JSON.stringify(morningPod));
        console.log("morningAvailability:"+ JSON.stringify(morningAvailability));
        console.log("afternoonPod:"+ JSON.stringify(afternoonPod));
        console.log("afternoonAvailability:"+ JSON.stringify(afternoonAvailability));
    }
}
