import {UserDto} from "../user/user.model";
import {TimeSlot} from "../pod-assignment/time-slot.enum";

/**
 * Pod lead is trying to assign these Users to these pods for this duration
 */
export class PodAssignmentCreateRequestDto {

    constructor(public podId: string,
                public userIds: string[],
                public startDate: Date,
                public startTimeSlot: TimeSlot,
                public endDate: Date,
                public endTimeSlot: TimeSlot) {
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

export class PodAssignmentCreateRequestDayTemp {

    constructor(public day: Date,
                public morningTimeSlot: TimeSlot | null,
                public afternoonTimeSlot: TimeSlot| null) {
    }

}
