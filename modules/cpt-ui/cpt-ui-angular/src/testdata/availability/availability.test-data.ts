//test data for cpt_availability
import { AvailabilityType } from "../../app/scheduler/models/availability/availability.enum";

export const AVAILABILITY_TEST_DATA = [
  {
    uuid:'availabilityAbsentUuid',
    availabilityType: AvailabilityType.ABSENT,
    availabilityDescription:'As per WAY member is not working, reason could be anything for e.g. vacations, sickness, attending conference etc',
    availabilityTypeStyleClass: 'absent'
  },
  {
    uuid:'availabilityPublicHolidaysUuid',
    availabilityType: AvailabilityType.PUBLIC_HOLIDAY,
    availabilityDescription:'Globally Saturday and Sunday are treated as public holidays.',
    availabilityTypeStyleClass: 'publicHoliday'
  },
  {
    uuid:'availabilityAvailableUuid',
    availabilityType: AvailabilityType.AVAILABLE,
    availabilityDescription:'User is available for booking.',
    availabilityTypeStyleClass: 'available'
  },
];

