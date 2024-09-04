import {TimeSlot} from "../pod-assignment/time-slot.enum";
import {AssignmentDto} from "../pod-assignment/assignment.model";

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

export class DayToAssign {

    isDataValid: boolean = false;

    constructor(public dayInAction: Date,
                public timeSlotInAction: TimeSlot) {
        this.isDataValid = !!(timeSlotInAction && dayInAction);
    }

}

export class DayAndSlotToAssign {

    constructor(public day: Date,
                public morningTimeSlot: TimeSlot | null,
                public afternoonTimeSlot: TimeSlot| null,
                public assignmentInAction: AssignmentDto) {
    }

    equals (that: DayAndSlotToAssign) {
        return this.day === that.day && this.morningTimeSlot === that.morningTimeSlot && this.afternoonTimeSlot === that.afternoonTimeSlot;
    }

}
