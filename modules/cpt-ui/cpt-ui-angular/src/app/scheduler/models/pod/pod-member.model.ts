import { PodDto } from "./pod.model";
import { PodMemberRole } from "./pom-member-role.enum";
import { UserDto } from "../user/user.model";

/**
 * Model representing cpt_pod_member DB table.
 * Model to define employees who are part of the pod.
 * Mainly Developers, BAs, SAs etc and not the Line Managers, Crew leads, stream leads as they are not directly part of the pod.
 */
export class PodMemberDto {


  constructor(public uuid: string, //required, cpt_pod_member.uuid
              public user: UserDto,// required, user_uuid<fk>, cascade delete: when user is deleted this entry should also be deleted.
              public podMemberRole: PodMemberRole, //required
              public pod?: PodDto /*optional, user is part of all these pods*/) {
  }


}
