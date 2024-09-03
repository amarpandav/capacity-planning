import {PodDto} from "../pod/pod.model";
import {UserAssignmentDto} from "./user-assignment.model";

export class PodAssignmentViewDto {

    constructor(public currentPod: PodDto,
                public userAssignments: UserAssignmentDto[]) {
    }
}
