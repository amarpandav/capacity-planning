import {Component, DestroyRef, input, OnChanges, output} from '@angular/core';
import {SchedulerSettingsDto} from "./scheduler.settings.model";
import {AppConstants} from "../utils/AppConstants";
import {DateUtils} from "../utils/DateUtils";
import {PodService} from "../pod/pod.service";
import {UserDto} from "../scheduler/models/user/user.model";
import {MyPodInfoDto} from "../pod/my-pod.model";
import {Subscription} from "rxjs";
import {EntityId} from "../scheduler/models/entityId.model";

@Component({
    selector: 'app-scheduler-settings',
    standalone: true,
    imports: [],
    templateUrl: './scheduler-settings.component.html',
    styleUrl: './scheduler-settings.component.scss'
})
export class SchedulerSettingsComponent implements OnChanges {

    selectedUser = input<UserDto>();
    schedulerSettings: SchedulerSettingsDto;
    monthShortNames: string[] = [];
    monthsToView: number[] = [];
    selectedYear: number = new Date().getFullYear();

    startMonthToView: number = 0;
    noOfMonthsToView: number = 3;

    schedulerSettingsOutput = output<SchedulerSettingsDto>();

    myPods?: MyPodInfoDto[];

    selectedPodEntityIdOutput = output<EntityId<string>>();

    constructor(private destroyRef: DestroyRef, private podService: PodService) {
        this.monthShortNames = AppConstants.monthShortNames;
        this.monthsToView = AppConstants.NoOfMonthsToView;
        this.startMonthToView = DateUtils.currentMonth();

        this.schedulerSettings = SchedulerSettingsDto.newDefaultInstance();
    }

    ngOnChanges(): void {
        if (this.selectedUser()) {
            console.log("SchedulerSettingsComponent.ngOnChanges() - selected user changed:" + JSON.stringify(this.selectedUser()));

            // @ts-ignore
            const subscription1 = this.podService.findMyPods(this.selectedUser().entityId)
                .subscribe({
                        next: (myPods) => {
                            //console.log("myPods:"+JSON.stringify(myPods));
                            this.myPods = myPods;
                        }
                    }
                );

            //Destroy is optional
            this.destroySubscription(subscription1);
        }

    }

    private destroySubscription(subscription: Subscription) {
        this.destroyRef.onDestroy(() => {
            subscription.unsubscribe(); //technical this is not required but its a good idea
        });
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

    protected readonly JSON = JSON;

    onPodChanged(event: any) {
        console.log("pod changed:" + event.target.value);
        //console.log("pod changed:" + htmlOptionElement.value);
        this.selectedPodEntityIdOutput.emit(new EntityId(event.target.value));
    }
}
