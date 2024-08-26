import {UserDto} from "../user/user.model";
import {TimeSlot} from "./time-slot.enum";
import {PodDto} from "../pod/pod.model";

/**
 * Pod lead is trying to book assign these Users to these pods for this duration
 */
export class PodAssignmentToSave {

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

export class PodAssignmentToSaveTemp {

    isDataValid: boolean = false;

    constructor(public clickedUser: UserDto,
                public clickedTimeSlot: TimeSlot,
                public clickedDay: Date) {
        this.isDataValid = !!(clickedUser && clickedDay && clickedTimeSlot);
    }

}
