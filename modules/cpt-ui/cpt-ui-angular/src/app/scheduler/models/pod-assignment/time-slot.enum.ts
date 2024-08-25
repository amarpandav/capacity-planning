export enum TimeSlot{
  MORNING = 'MORNING',
  AFTERNOON = 'AFTERNOON'
}

export function toEnum(str: string) {
  return TimeSlot[str as keyof typeof TimeSlot];
}

export function isMorning(timeSlot: TimeSlot) {
  return TimeSlot.MORNING === timeSlot;
}

export function isAfternoon(timeSlot: TimeSlot) {
  return TimeSlot.AFTERNOON === timeSlot;
}

