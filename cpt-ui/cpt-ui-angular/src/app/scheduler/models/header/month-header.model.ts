import {AppConstants} from "../../../shared/utils/AppConstants";


export class MonthHeaderDto {
  monthAsString: string;

  constructor(public month: number, public noOfDays: number) {
    this.monthAsString = AppConstants.monthNames[month];
  }

  /**
   * Default instantiation
   * @return {MonthHeaderDto} month header model
   */
  public static newInstance(): MonthHeaderDto {
    return new MonthHeaderDto(0, 31);
  }

}
