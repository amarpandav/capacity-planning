import {PodMemberRole} from "./pom-member-role.enum";
import {UserDto} from "../user/user.model";

/**
 * Model representing cpt_pod_member DB table but grouped by user
 * Model to define employees who are part of the pod.
 * Mainly Developers, BAs, SAs etc and not the Line Managers, Crew leads, stream leads as they are not directly part of the pod.
 */
export class PodMemberDto {

    constructor(public uuid: string, //required, cpt_pod_member.uuid
                public podMemberRole: PodMemberRole,
                public user: UserDto) {
    }

}
