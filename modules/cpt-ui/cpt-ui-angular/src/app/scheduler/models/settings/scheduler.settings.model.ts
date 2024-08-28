export class SchedulerSettingsDto {
  yearToView: number;
  noOfMonthsToView: number;
  startMonthToView: number;
  endMonthToView: number;

  constructor(selectedYear: number, selectedMonth: number, selectedMonthsToView: number) {
    this.yearToView = selectedYear;
    this.startMonthToView = selectedMonth;
    this.noOfMonthsToView = selectedMonthsToView;

    this.startMonthToView = this.startMonthToView >= 12 ? 11 : this.startMonthToView; //in real app we do not need it as user will not enter number instead they will select month name directly

    let endMonth: number = this.noOfMonthsToView;

    this.endMonthToView = (this.startMonthToView + endMonth) >= 12 ? 12 : (this.startMonthToView + endMonth);
  }

  public static newInstance(selectedYear: number, selectedMonth: number, selectedMonthsToView: number) {
    return new SchedulerSettingsDto(selectedYear, selectedMonth, selectedMonthsToView);
  }

}
