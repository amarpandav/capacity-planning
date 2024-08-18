import {UserCapacityDto} from "../user/user.capacity.model";
import {PodMemberRole} from "../pod/pom-member-role.enum";
import {UserDto} from "../user/user.model";
/**
 * <ul>Virtual Model representing mix of following tables representing capacity planning grouped by User to display scheduler view.
 * <li>cpt_user</li>
 * <li>cpt_user_booked_capacity</li>
 * <li>cpt_availability</li>
 * </ul>
 */
export class SchedulerViewDto {

  /**
   * Why we need cpt_pod_member_uuid<fk> and not user_uuid:
   * Because we need Pod member's role too to display on the UI.
   * @param user
   * @param podMemberRoles
   * @param userCapacities
   */
  constructor(public user: UserDto,// required, user_uuid<fk>, cascade delete: when user is deleted this entry should also be deleted.
              public podMemberRoles: PodMemberRole[], //this user can be part of multiple pods with different roles for e.g. Amar Developer in AURA and SA in GIM.
              public userCapacities: UserCapacityDto[] | null) {
  }
}
