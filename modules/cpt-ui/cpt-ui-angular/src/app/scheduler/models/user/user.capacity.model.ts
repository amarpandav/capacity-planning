import { UserBookedCapacityDto } from "./user-booked-capacity-model";
import { UserAvailableCapacityDto } from "./user-available-capacity.model";
import { DateUtils } from "../../../shared/utils/DateUtils";

/**
 * <ul>Virtual wrapper Model wrapping following tables representing capacity planning grouped by User to display scheduler view.
 * <li>cpt_user_booked_capacity</li>
 * <li>cpt_availability</li>
 * </ul>
 */
export class UserCapacityDto {


  constructor(/*public day: Date | null, TODO: pod-member-bookings-test-data.ts is not working with Date type*/
              public dayAsStr?: string | null,
              public day?: Date | null, ////Either dayAsStr or day object
              public userBookedCapacity?: UserBookedCapacityDto | null,
              public userAvailableCapacity?: UserAvailableCapacityDto | null) {
    if(dayAsStr && !day){
      this.day = DateUtils.parseISODate(dayAsStr);
    }

    if (!userBookedCapacity && !userAvailableCapacity) {
      //You can't have an empty capacity day
      //TODO we can't inject service in a Dto hence move this code later into an injectable component.
      //TODO convert day to DD.MM.YYYY
      //this.toastrService.error('Something went wrong: userBookedCapacity and userAvailableCapacity both can not be empty');
      console.error('Something went wrong: userBookedCapacity and userAvailableCapacity both can not be empty');
    }
  }


}
