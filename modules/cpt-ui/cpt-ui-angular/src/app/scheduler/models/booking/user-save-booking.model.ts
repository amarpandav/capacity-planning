import {UserCapacityDto} from "../user/user.capacity.model";
import {PodDto} from "../pod/pod.model";
import {AvailabilityDto} from "../availability/availability.model";
import {UserDto} from "../user/user.model";

/**
 * Pod lead is trying to book this User
 */
export class UserSaveBookingDto {


    isDataValid: boolean = false;

    constructor(public user: UserDto,
                public userCapacity: UserCapacityDto,
                public morningPod?: PodDto | null,
                public afternoonPod?: PodDto | null,
                public morningAvailability?: AvailabilityDto | null,
                public afternoonAvailability?: AvailabilityDto | null) {
        if (user && userCapacity && (morningPod || afternoonPod || morningAvailability || afternoonAvailability)) {
            this.isDataValid = true;
        } else {
            this.isDataValid = false;
        }

    }


}
