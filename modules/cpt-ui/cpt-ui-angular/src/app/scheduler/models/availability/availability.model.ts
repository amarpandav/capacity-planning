import { AvailabilityType } from "./availability.enum";

/**
 * Model representing cpt_availability DB table.
 * This is kind of static table with 3 rows, one row for each AvailabilityType.
 *
 */
export class AvailabilityDto {

  constructor(public uuid: string | null,
              public availabilityType: AvailabilityType,
              public availabilityDescription: string,
              public availabilityTypeStyleClass: string) {
  }

}
