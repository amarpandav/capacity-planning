import {TimeSlot} from "../pod-assignment/time-slot.enum";
import {UserDto} from "../user/user.model";

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

export class StartOrEndDayToAssign {

    isDataValid: boolean = false;

    constructor(public userInAction: UserDto,
                public dayInAction: Date,
                public timeSlotInAction: TimeSlot) {
        this.isDataValid = !!(timeSlotInAction && dayInAction);
    }

}

export class FlexDayToRepaint {

    constructor(public day: Date,
                public timeSlotInAction: TimeSlot/*,
                public assignmentInAction: AssignmentDto*/) {
    }

    equals (that: FlexDayToRepaint) {
        return this.day.toString() === that.day.toString() && this.timeSlotInAction === that.timeSlotInAction;
        //TODO not working - return this.day.getTime() === that.day.getTime() && this.timeSlotInAction === that.timeSlotInAction;
    }

}
