export enum AvailabilityType{
  AVAILABLE = 'AVAILABLE',
  ABSENT = 'ABSENT',
  PUBLIC_HOLIDAY = 'PUBLIC_HOLIDAY',
  POD_ASSIGNMENT = 'POD_ASSIGNMENT',
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
export function isPodAssignment(availability: AvailabilityType) {
  return AvailabilityType.POD_ASSIGNMENT === availability;
}

