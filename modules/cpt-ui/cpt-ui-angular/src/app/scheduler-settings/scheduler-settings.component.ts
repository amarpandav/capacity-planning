import {Component, DestroyRef, input, OnChanges, output} from '@angular/core';
import {SchedulerSettingsDto} from "./scheduler.settings.model";
import {AppConstants} from "../utils/AppConstants";
import {DateUtils} from "../utils/DateUtils";
import {PodService} from "../pod/pod.service";
import {UserDto} from "../scheduler/models/user/user.model";
import {Subscription} from "rxjs";
import {PodDto} from "../scheduler/models/pod/pod.model";
import {AvailabilityType} from "../scheduler/models/availability/availability.enum";
import {
    getPodMemberRoleKeys,
    PodMemberRole,
    getPodMemberRoleValue, getPodMemberRoleKeysTemp
} from "../scheduler/models/pod/pom-member-role.enum";

@Component({
    selector: 'app-scheduler-settings',
    standalone: true,
    imports: [],
    templateUrl: './scheduler-settings.component.html',
    styleUrl: './scheduler-settings.component.scss'
})
export class SchedulerSettingsComponent implements OnChanges {

    selectedUser = input<UserDto>();
    relatedPodMemberRoles = input<PodMemberRole[]>();
    schedulerSettings: SchedulerSettingsDto;
    monthShortNames: string[] = [];
    monthsToView: number[] = [];
    selectedYear: number = new Date().getFullYear();

    startMonthToView: number = 0;
    noOfMonthsToView: number = 3;

    schedulerSettingsOutput = output<SchedulerSettingsDto>();

    myPods?: PodDto[];
    relatedPods?: PodDto[];
    availabilityTypes: AvailabilityType[] = [];
    podMemberRoles: PodMemberRole[] = [];

    selectedMyPod?: PodDto;
    selectedMyPodOutput = output<PodDto>();

    selectedPodToAssign?: PodDto;
    selectedPodToAssignOutput = output<PodDto | undefined>();

    selectedAvailabilityType?: AvailabilityType;
    selectedAvailabilityTypeOutput = output<AvailabilityType | undefined>();

    constructor(private destroyRef: DestroyRef, private podService: PodService) {
        this.monthShortNames = AppConstants.monthShortNames;
        this.monthsToView = AppConstants.NoOfMonthsToView;
        this.startMonthToView = DateUtils.currentMonth();

        this.schedulerSettings = SchedulerSettingsDto.newDefaultInstance();

        this.availabilityTypes.push(AvailabilityType.AVAILABLE);
        this.availabilityTypes.push(AvailabilityType.PUBLIC_HOLIDAY);
        this.availabilityTypes.push(AvailabilityType.ABSENT);

        this.podMemberRoles.push(PodMemberRole.JAVA_DEVELOPER);
    }

    ngOnChanges(): void {
        if (this.selectedUser()) {
            //console.log("SchedulerSettingsComponent.ngOnChanges() - selected user changed:" + JSON.stringify(this.selectedUser()));

            // @ts-ignore
            const subscription1 = this.podService.findMyPods(this.selectedUser().entityId)
                .subscribe({
                        next: (myPods) => {
                            //console.log("myPods:"+JSON.stringify(myPods));
                            this.myPods = myPods;
                            //as the screen loads we would like to select first my pod from this list
                            if(this.myPods && this.myPods.length > 0){
                                this.emitPodChangedEvent(this.myPods[0]);
                            }
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

    onMyPodChanged(event: any) {
        //console.log("onMyPodChanged :" + JSON.stringify(event.target.value));
        let podDto = this.myPods?.find( (podDto)=>podDto.entityId.uuid === event.target.value);
        //console.log("pod changed:" + htmlOptionElement.value);
        if(podDto){
            this.emitPodChangedEvent(podDto);
        }

    }

    emitPodChangedEvent(podDto: PodDto){
        this.selectedMyPodOutput.emit(podDto);
        this.selectedMyPod = podDto;
        //console.log("emitPodChangedEvent:"+JSON.stringify(podDto));

        // @ts-ignore
        const subscription1 = this.podService.findRelatedPods(this.selectedMyPod.entityId)
            .subscribe({
                    next: (relatedPods) => {
                        //console.log("relatedPods:"+JSON.stringify(relatedPods));
                        this.relatedPods = relatedPods;
                    }
                }
            );

        //Destroy is optional
        this.destroySubscription(subscription1);
    }

    onSelectRelatedPod(pod: PodDto) {
        this.selectedPodToAssign = pod;
        this.selectedPodToAssignOutput.emit(pod);

        this.selectedAvailabilityType = undefined;
        this.selectedAvailabilityTypeOutput.emit(undefined);

    }

    onSelectAvailabilityType(at: AvailabilityType) {
        this.selectedAvailabilityType = at;
        this.selectedAvailabilityTypeOutput.emit(at);

        this.selectedPodToAssign = undefined;
        this.selectedPodToAssignOutput.emit(undefined);
    }

    protected readonly getPodMemberRoleKeys = getPodMemberRoleKeys;
    protected readonly getPodMemberRoleValue = getPodMemberRoleValue;
    protected readonly getPodMemberRoleKeysTemp = getPodMemberRoleKeysTemp;
}
