import { AppConstants } from "../utils/AppConstants";


export class MonthHeaderDto {
  monthAsString: string;

  /**
   * @param showYear true when the month is outside the selected year, e.g. "January 2027" in a view starting in November 2026
   */
  constructor(public month: number, public year: number, public noOfDays: number, showYear: boolean) {
    this.monthAsString = AppConstants.monthNames[month] + (showYear ? ' ' + year : '');
  }

}
