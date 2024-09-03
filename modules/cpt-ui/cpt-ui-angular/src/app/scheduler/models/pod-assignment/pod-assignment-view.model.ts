import {PodDto} from "../pod/pod.model";
import {PodAssignmentWrapperDto} from "./pod-assignment-wrapper.model";

export class PodAssignmentViewDto {

    constructor(public currentPod: PodDto,
                public podAssignmentWrappers: PodAssignmentWrapperDto[]) {
    }
}
