import {DateUtils} from "../../../utils/DateUtils";
import {AssignmentDto} from "./assignment.model";


export class PodAssignmentDto {

    constructor(public uuid: string, //cpt_pod_assignment.uuid
                public morning: AssignmentDto,
                public afternoon: AssignmentDto,
                public dayAsStr?: string | null,
                public day?: Date | null, ////Either dayAsStr or day object
                )
                {
        if (this.dayAsStr && !this.day) {
            this.day = DateUtils.parseISODate(this.dayAsStr);
        }

        if (!morning && !afternoon) {
            //You can't have an empty capacity day
            //TODO we can't inject service in a Dto hence move this code later into an injectable component.
            //TODO convert day to DD.MM.YYYY
            //this.toastrService.error('Something went wrong: userBookedCapacity and userAvailableCapacity both can not be empty');
            console.error('Something went wrong: morning and afternoon AssignmentDto both can not be empty');
        }
    }

}
