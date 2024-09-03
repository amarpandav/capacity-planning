import {AssignmentDto} from "./assignment.model";
import {DateUtils} from "../../../utils/DateUtils";


export class PodAssignmentDto {

    public isWeekend: boolean = false;

    constructor(public uuid: string, //cpt_pod_assignment.uuid
                public day: Date,
                public morning: AssignmentDto,
                public afternoon: AssignmentDto){
        this.isWeekend = DateUtils.isWeekend(day);
        if (!morning && !afternoon) {
            //You can't have an empty capacity day
            //TODO we can't inject service in a Dto hence move this code later into an injectable component.
            //TODO convert day to DD.MM.YYYY
            //this.toastrService.error('Something went wrong: userBookedCapacity and userAvailableCapacity both can not be empty');
            console.error('Something went wrong: morning and afternoon AssignmentDto both can not be empty');
        }
    }

}
