import { AvailabilityDto } from "../availability/availability.model";
import { DateUtils } from "../../../shared/utils/DateUtils";

/**
 * <ul>Model representing cpt_user_available_capacity DB table.
 * <li>AvailabilityType.ABSENT and PUBLIC_HOLIDAY: imp_way (imported data from goto/WAY) data is cleansed and pushed into cpt_user_available_capacity for cpt usage.</li>
 * <li>AvailabilityType.ABSENT and PUBLIC_HOLIDAY: Every day as soon as imp_way import finishes, cpt_user_available_capacity needs to be re-calculated/updated.</li>
 * <li>AvailabilityType.AVAILABLE: We can store AvailabilityType.AVAILABLE per user but I recommend to not. This way we save DB space. AvailabilityType.AVAILABLE can be determined virtually when morning/afternoon pod is empty and AvailabilityType.ABSENT and PUBLIC_HOLIDAY is also empty. </li>
 *</ul>
 * DB-Constraints:
 * Either one of is required: cpt_morning_availability_uuid or cpt_afternoon_availability_uuid
 *
 * Columns:
 * uuid
 * day
 * cpt_morning_availability_uuid
 * cpt_afternoon_availability_uuid
 *
 */
export class UserAvailableCapacityDto {

  constructor(public uuid: string, //required, cpt_user_available_capacity.uuid
              /*public day: Date | null, TODO: respective test-data.ts file is not working with Date type*/
              public dayAsStr?: string | null, //required in DB, I don't need it for UI as I am using it from UserCapacityDto,
              public day?: Date | null, ////Either dayAsStr or day object
              public morningAvailability?: AvailabilityDto | null,
              public afternoonAvailability?: AvailabilityDto | null) {
    if(dayAsStr && !day){
      this.day = DateUtils.parseISODate(dayAsStr);
    }

    //console.log(day);
    //if (day === '13.08.2024') {
      /*console.log("bye");*/
      /*let date = DateUtils.parse('2024.08.13');
      console.log(date);
      ;
      console.log(DateUtils.formatToISO(date));*/
      /*console.log(DateUtils.format(day))
      let date2 = Date.parse(day);*/
      /*let date2 = new Date('13.08.2024').toLocaleDateString(
        'de-CH',
        {
          day: 'numeric',
          month: 'long',
          year: 'numeric'
        }
        //{day:'2-digit', month:'2-digit', year:'numeric' }
      );*/


      /*let s = new Intl.DateTimeFormat('de-CH', {day:'2-digit', month:'2-digit', year:'numeric' }).format(date);
      new Intl.DateTi
      console.log(s);*/
   // }
  }

}
