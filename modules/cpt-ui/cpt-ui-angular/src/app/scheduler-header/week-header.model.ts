export class WeekHeaderDto {


  noOfDays: number = 0;

  constructor(public weekNumber: number) {
  }

  increaseNoOfDaysByOne() {
    this.noOfDays = this.noOfDays + 1;
  }

}
