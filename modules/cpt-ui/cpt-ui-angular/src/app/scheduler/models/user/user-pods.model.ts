import {PodDto} from "../pod/pod.model";
import {PodMemberRole} from "../pod/pom-member-role.enum";
import {UserDto} from "./user.model";

/**
 * podMember is part of these pods
 */
export class UserPodsDto {

    constructor(public user: UserDto,
                public userPodDetails: UserPodDetailsDto[]) {
    }

}

export class UserPodDetailsDto {

    constructor(public podMemberUuid: string, //PodMember.uuid
                public podMemberRole: PodMemberRole,
                public pod: PodDto) {
    }

}
