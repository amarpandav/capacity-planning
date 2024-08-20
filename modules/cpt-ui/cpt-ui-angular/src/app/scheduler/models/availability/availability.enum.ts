export enum AvailabilityType{
  AVAILABLE = 'Available',
  ABSENT = 'Absent',
  PUBLIC_HOLIDAY = 'Public Holiday',
}

export function toEnum(str: string) {
  return AvailabilityType[str as keyof typeof AvailabilityType];
}

export function isAvailable(availability: AvailabilityType) {
  return AvailabilityType.AVAILABLE === availability;
}

export function isAbsent(availability: AvailabilityType) {
  return AvailabilityType.ABSENT === availability;
}

export function isPublicHoliday(availability: AvailabilityType) {
  return AvailabilityType.PUBLIC_HOLIDAY === availability;
}

