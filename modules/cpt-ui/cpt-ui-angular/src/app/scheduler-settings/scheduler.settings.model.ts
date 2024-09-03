/**
 * startMonthToView: 0 is Jan...11 is Dec
 * noOfMonthsToView: How many month you want view starting form startMonthToView.
 * When startMonthToView = 1 and noOfMonthsToView = 3, then user want to see Feb, March and April.
 */
export class SchedulerSettingsDto {

  endMonthToView: number;

  startDate: Date;

  endDate: Date;

  constructor(public yearToView: number, public startMonthToView: number, public noOfMonthsToView: number) {

    this.startMonthToView = this.startMonthToView >= 12 ? 11 : this.startMonthToView; //in real app we do not need it as user will not enter number instead they will select month name directly

    let endMonth: number = this.noOfMonthsToView - 1;

    this.endMonthToView = (this.startMonthToView + endMonth) >= 12 ? 12 : (this.startMonthToView + endMonth);

    this.startDate = new Date(yearToView, startMonthToView, 1);
    this.endDate = new Date(yearToView, this.endMonthToView+1, 1); // go to start of next month
    this.endDate.setDate(this.endDate.getDate()-1); //go back 1 day which would be end of that month

  }

  public static newInstance(selectedYear: number, selectedMonth: number, selectedMonthsToView: number) {
    return new SchedulerSettingsDto(selectedYear, selectedMonth, selectedMonthsToView);
  }

  public static newDefaultInstance(){
    return new SchedulerSettingsDto(new Date().getFullYear(), new Date().getMonth(), 3)
  }

}
