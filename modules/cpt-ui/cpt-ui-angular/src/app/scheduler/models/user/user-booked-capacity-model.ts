import { PodDto } from "../pod/pod.model";
import { UserDto } from "./user.model";
import { DateUtils } from "../../../shared/utils/DateUtils";

/**
 * Model representing cpt_user_booked_capacity DB table.
 *
 * Table: cpt_user_booked_capacity
 *
 * DB-Constraints:
 * Either of is required: cpt_pod_uuid_xxx or cpt_absence_uuid_xxx
 *
 * Columns:
 * uuid
 * user_uuid<fk>: cascade delete when user gets deleted
 * cpt_pod_morning_uuid<fk> : no cascade delete when cpt_pod gets deleted otherwise we loose everything booked for that person. On the contrary when cpt_pod gets deleted we must keep this row and set cpt_pod_uuid_morning_uuid to null for all future dates starting from now.
 * cpt_pod_afternoon_uuid<fk>: same like above
 *
 *
 **/
export class UserBookedCapacityDto {

  constructor(public uuid: string, //required, cpt_user_booked_capacity.uuid
              /*public day: Date | null, TODO: respective test-data.ts file is not working with Date type*/
              public dayAsStr?: string | null, //required in DB, I don't need it for UI as I am using it from UserCapacityDto, hence optional with ?
              public day?: Date | null, //Either dayAsStr or day object
              public user?: UserDto, //required in DB, I don't need it for UI as I am using it from UserCapacityDto, hence option with ?
              public morningPod?: PodDto | null,
              public afternoonPod?: PodDto | null) {
    if(dayAsStr && !day){
      this.day = DateUtils.parseISODate(dayAsStr);
    }

    if (!morningPod && !afternoonPod) {
      //You can't have an empty booking
      //TODO we can't inject service in a Dto hence move this code later into an injectable component.
      //TODO convert day to DD.MM.YYYY
      //this.toastrService.error('Something went wrong: Empty booking found for day: '+day);
      console.error('Something went wrong: Empty booking found for cpt_user_booked_capacity.uuid: ' + this.uuid);
    }
  }

 /* addMorningPod(morningPod: PodDto){
    this.morningPod = morningPod;
  }
  addAfternoonPod(afternoonPod: PodDto){
    this.afternoonPod = afternoonPod;
  }*/
}
