/**
 * startMonthToView: 0 is Jan...11 is Dec
 * noOfMonthsToView: How many month you want view starting form startMonthToView.
 * When startMonthToView = 1 and noOfMonthsToView = 3, then user want to see Feb, March and April.
 */
export class SchedulerSettingsDto {

  startDate: Date;

  endDate: Date;

  constructor(public yearToView: number, public startMonthToView: number, public noOfMonthsToView: number) {

    this.startMonthToView = this.startMonthToView >= 12 ? 11 : this.startMonthToView; //in real app we do not need it as user will not enter number instead they will select month name directly

    this.startDate = new Date(yearToView, this.startMonthToView, 1);
    // Day 0 of the month after the last viewed month is the last viewed day; Date rolls over into the next year,
    // e.g. December + 3 months ends on the last day of February next year.
    this.endDate = new Date(yearToView, this.startMonthToView + this.noOfMonthsToView, 0);

  }

  public static newInstance(selectedYear: number, selectedMonth: number, selectedMonthsToView: number) {
    return new SchedulerSettingsDto(selectedYear, selectedMonth, selectedMonthsToView);
  }

  public static newDefaultInstance(){
    return new SchedulerSettingsDto(new Date().getFullYear(), new Date().getMonth(), 3)
  }

}
