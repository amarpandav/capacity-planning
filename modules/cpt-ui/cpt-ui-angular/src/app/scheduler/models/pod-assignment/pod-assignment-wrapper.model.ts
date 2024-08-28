import {PodMemberRole} from "../pod/pom-member-role.enum";
import {UserDto} from "../user/user.model";
import {PodAssignmentDto} from "./pod-assignment.model";

/**
 * Virtual wrapper Model
 */
export class PodAssignmentWrapperDto {

  constructor(public user: UserDto,// required, user_uuid<fk>, cascade delete: when user is deleted this entry should also be deleted.
              public podMemberRole: PodMemberRole, //this user can be part of multiple pods with different roles for e.g. Amar Developer in AURA and SA in GIM.
              public podAssignments: PodAssignmentDto[]) {
  }
}
