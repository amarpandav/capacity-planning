export class DayHeaderDto {

  constructor(public day: Date) {
  }

  /**
   * Default instantiation
   * @return {DayHeaderDto} day header model
   */
  public static newInstance(): DayHeaderDto {
    return new DayHeaderDto(new Date());
  }

}
