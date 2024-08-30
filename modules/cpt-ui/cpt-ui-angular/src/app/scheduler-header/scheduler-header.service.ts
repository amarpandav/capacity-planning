import {Injectable} from "@angular/core";
import {SchedulerSettingsDto} from "../scheduler/models/settings/scheduler.settings.model";
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
        for (let month = schedulerSettings.startMonthToView; month <= schedulerSettings.endMonthToView; month++) {
            //this.monthsToView.push(SchedulerDto.monthNames[month]);
            let noOfDays = new Date(schedulerSettings.yearToView, month + 1, 0).getDate();
            let monthDto = new MonthHeaderDto(month, noOfDays);
            monthHeaders.push(monthDto);
        }

        //populate days header
        let dayHeaders : DayHeaderDto[] = [];
        monthHeaders.forEach((monthHeaderDto: MonthHeaderDto) => {
            let month = monthHeaderDto.month;
            var date = new Date(schedulerSettings.yearToView, month, 1);

            while (date.getMonth() === month) {
                dayHeaders.push(new DayHeaderDto(new Date(date)));
                date.setDate(date.getDate() + 1);
            }
        });

        //populate week header
        let weekHeaders : WeekHeaderDto[] = [];
        
        dayHeaders.forEach((dayHeaderDto: DayHeaderDto) => {
            let weekNumber = parseInt("" + this.datePipe.transform(dayHeaderDto.day, 'w'));
            let weekHeaderDto = weekHeaders.find(week => week.weekNumber === weekNumber);
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
