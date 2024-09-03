import {Component, output} from '@angular/core';
import {SchedulerSettingsDto} from "./scheduler.settings.model";
import {AppConstants} from "../utils/AppConstants";
import {DateUtils} from "../utils/DateUtils";

@Component({
    selector: 'app-scheduler-settings',
    standalone: true,
    imports: [],
    templateUrl: './scheduler-settings.component.html',
    styleUrl: './scheduler-settings.component.scss'
})
export class SchedulerSettingsComponent {

    schedulerSettings: SchedulerSettingsDto;
    monthShortNames: string[] = [];
    monthsToView: number[] = [];
    selectedYear: number = new Date().getFullYear();

    startMonthToView: number = 0;
    noOfMonthsToView: number = 3;

    schedulerSettingsOutput = output<SchedulerSettingsDto>();

    constructor() {
        this.monthShortNames = AppConstants.monthShortNames;
        this.monthsToView = AppConstants.NoOfMonthsToView;
        this.startMonthToView = DateUtils.currentMonth();

        this.schedulerSettings = SchedulerSettingsDto.newDefaultInstance();
    }

    onMonthChange(monthNumber: number) {
        this.startMonthToView = monthNumber;
        this.schedulerSettings = SchedulerSettingsDto.newInstance(this.selectedYear, this.startMonthToView, this.noOfMonthsToView);
        this.schedulerSettingsOutput.emit(this.schedulerSettings);
    }

    protected readonly AppConstants = AppConstants;

    onNoOfMonthsToViewChange(noOfMonthsToView: number) {
        this.noOfMonthsToView = noOfMonthsToView;
        this.schedulerSettings = SchedulerSettingsDto.newInstance(this.selectedYear, this.startMonthToView, this.noOfMonthsToView);
        this.schedulerSettingsOutput.emit(this.schedulerSettings);
    }
}
