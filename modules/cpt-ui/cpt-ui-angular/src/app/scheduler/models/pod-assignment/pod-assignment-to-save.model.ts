import {UserDto} from "../user/user.model";
import {TimeSlot} from "./time-slot.enum";

/**
 * Pod lead is trying to book this User
 */
export class PodAssignmentToSave {

    isDataValid: boolean = false;

    constructor(public selectedUser: UserDto,
                public selectedTimeSlot: TimeSlot,
                public day: Date) {
        this.isDataValid = !!(selectedUser && day && selectedTimeSlot);
    }

}
