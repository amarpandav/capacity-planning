import {UserAssignmentDto} from "./user-assignment.model";

export class PodAssignmentViewDto {

    constructor(public userAssignments: UserAssignmentDto[]) {
    }
}
