import { AppConstants } from "../../../shared/utils/AppConstants";


export class MonthHeaderDto {
  monthAsString: string;

  constructor(public month: number, public noOfDays: number) {
    this.monthAsString = AppConstants.monthNames[month];
  }

}
