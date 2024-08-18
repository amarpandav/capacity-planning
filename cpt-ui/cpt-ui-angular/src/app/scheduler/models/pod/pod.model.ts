import {PodWatcherDto} from "./pod.watcher.model";
import {AuditInfoDto} from "../shared/audit-info.model";
import {PodMemberDto} from "./pod-member.model";

/**
 * Model representing cpt_pod DB table.
 */
export class PodDto {

  constructor(public uuid: string, //mandatory
              public podName: string,//mandatory
              public podStyleClass: string,//mandatory
              public podDescription: string,//mandatory
              public auditInfo?: AuditInfoDto | null,
              public podMembers?: PodMemberDto[] | null,
              public podWatchers?: PodWatcherDto[] | null) {
  }

}
