import {UserDto} from "../user/user.model";
import {TimeSlot} from "../pod-assignment/time-slot.enum";
import {PodDto} from "../pod/pod.model";

/**
 * Pod lead is trying to assign these Users to these pods for this duration
 */
export class PodAssignmentCreateRequestDto {

    isDataValid: boolean = false;

    constructor(public usersToAssign: UserDto[],
                public podToAssign: PodDto,
                public startDay: Date,
                public startDayTimeSlot: TimeSlot,
                public endDay: Date,
                public endDayTimeSlot: TimeSlot) {
        this.isDataValid = !!(usersToAssign && podToAssign && startDay && startDayTimeSlot && endDay && endDayTimeSlot);
    }

}

export class PodAssignmentCreateRequestTemp {

    isDataValid: boolean = false;

    constructor(public userInAction: UserDto,
                public timeSlotInAction: TimeSlot,
                public dayInAction: Date) {
        this.isDataValid = !!(userInAction && timeSlotInAction && dayInAction);
    }

}
