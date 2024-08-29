import {MonthHeaderDto} from "./month-header.model";
import {DayHeaderDto} from "./day-header.model";
import {WeekHeaderDto} from "./week-header.model";


export class SchedulerHeaderDto {

    constructor(public monthHeaders: MonthHeaderDto[], public dayHeaders: DayHeaderDto[], public weekHeaders: WeekHeaderDto[]) {

    }

}
