export class WeekHeaderDto {


  noOfDays: number = 0;

  constructor(public weekNumber: number) {
  }

  increaseNoOfDaysByOne() {
    this.noOfDays = this.noOfDays + 1;
  }

  /**
   * Default instantiation
   * @return {WeekHeaderDto} week header model
   */
  public static newInstance(): WeekHeaderDto {
    return new WeekHeaderDto(1);
  }

}
