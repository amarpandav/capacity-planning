import {AvailabilityType} from "../availability/availability.enum";
import {PodDto} from "../pod/pod.model";

export class AssignmentDto {

  constructor(public availabilityType: AvailabilityType,
              public pod?: PodDto) {
  }

}
