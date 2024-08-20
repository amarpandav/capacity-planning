import { PodDto } from "./pod.model";
import { UserDto } from "../user/user.model";

/**
 * Model representing cpt_pod_watcher DB table.
 * Model to define employees who want to watch the pods.
 * Mainly Line Managers, Crew leads, stream leads who are not directly part of the pod.
 */
export class PodWatcherDto {

  constructor(public uuid: string, //required, cpt_pod_watcher.uuid
              public user: UserDto, //required
              public pods?: PodDto[] /*required, user is watching these pods*/) {
  }

}
