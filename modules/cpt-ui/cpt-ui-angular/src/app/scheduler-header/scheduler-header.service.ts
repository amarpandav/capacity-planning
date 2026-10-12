import {Injectable} from "@angular/core";
import {SchedulerSettingsDto} from "../scheduler-settings/scheduler.settings.model";
import {SchedulerHeaderDto} from "./scheduler-header.model";
import {MonthHeaderDto} from "./month-header.model";
import {DayHeaderDto} from "./day-header.model";
import {WeekHeaderDto} from "./week-header.model";
import {DatePipe} from "@angular/common";

@Injectable({providedIn: 'root'})
export class SchedulerHeaderService {

    constructor(private datePipe: DatePipe) {
    }

    //dialog is displayed inside AppComponent.ts
    findSchedulerHeader(schedulerSettings: SchedulerSettingsDto): SchedulerHeaderDto {
        //populate month header
        let monthHeaders : MonthHeaderDto[] = [];
        // Walk real dates so a view starting late in the year continues into the next year.
        for (let i = 0; i < schedulerSettings.noOfMonthsToView; i++) {
            let firstDay = new Date(schedulerSettings.yearToView, schedulerSettings.startMonthToView + i, 1);
            let noOfDays = new Date(firstDay.getFullYear(), firstDay.getMonth() + 1, 0).getDate();
            monthHeaders.push(new MonthHeaderDto(firstDay.getMonth(), firstDay.getFullYear(), noOfDays,
                firstDay.getFullYear() !== schedulerSettings.yearToView));
        }

        //populate days header
        let dayHeaders : DayHeaderDto[] = [];
        monthHeaders.forEach((monthHeaderDto: MonthHeaderDto) => {
            let month = monthHeaderDto.month;
            var date = new Date(monthHeaderDto.year, month, 1);

            while (date.getMonth() === month) {
                dayHeaders.push(new DayHeaderDto(new Date(date)));
                date.setDate(date.getDate() + 1);
            }
        });

        //populate week header
        let weekHeaders : WeekHeaderDto[] = [];
        
        dayHeaders.forEach((dayHeaderDto: DayHeaderDto) => {
            let weekNumber = parseInt("" + this.datePipe.transform(dayHeaderDto.day, 'w'));
            // days are consecutive, so a day belongs to the last week unless its week number changed
            // (looking up by number alone would merge e.g. week 1 of two different years)
            let weekHeaderDto = weekHeaders.length > 0 && weekHeaders[weekHeaders.length - 1].weekNumber === weekNumber
                ? weekHeaders[weekHeaders.length - 1] : undefined;
            if (weekHeaderDto) {
                //We already exists
                weekHeaderDto.increaseNoOfDaysByOne();
            } else {
                weekHeaderDto = new WeekHeaderDto(weekNumber);
                weekHeaderDto.increaseNoOfDaysByOne();
                weekHeaders.push(weekHeaderDto)
            }
        });

        return new SchedulerHeaderDto(monthHeaders, dayHeaders, weekHeaders);
    }



}
