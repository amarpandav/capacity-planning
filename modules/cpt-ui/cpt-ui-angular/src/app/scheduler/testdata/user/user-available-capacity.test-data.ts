import { AvailabilityType } from "../../models/availability/availability.enum";

export const USER_AVAILABLE_CAPACITY_TEST_DATA = [
  {
    uuid: 'AmarPandavAvailableCapacityUuid-120824',
    dayAsStr: '2024-08-12',
    morningAvailability: {
      uuid:'absence1Uuid',
      availabilityType: AvailabilityType.ABSENT,
      availabilityDescription:'As per way member is not working, it could be any reason for e.g. vacations, sickness, attending conference etc',
      availabilityTypeStyleClass: 'absent'
    },
    afternoonAvailability: {
      uuid:'absence1Uuid',
      availabilityType: AvailabilityType.ABSENT,
      availabilityDescription:'As per way member is not working, it could be any reason for e.g. vacations, sickness, attending conference etc',
      availabilityTypeStyleClass: 'absent'
    }
  },
  {
    uuid: 'KamilLipinskiAvailableCapacityUuid-090824',
    dayAsStr: '2024-08-09',
    afternoonAvailability: {
      uuid:'absence1Uuid',
      availabilityType: AvailabilityType.ABSENT,
      availabilityDescription:'As per way member is not working, it could be any reason for e.g. vacations, sickness, attending conference etc',
      availabilityTypeStyleClass: 'absent'
    }
  }
]
