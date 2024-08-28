import {PodDto} from "./pod.model";
import {PodWatcherDto} from "./pod-watcher.model";

/**
 * podWatcher is watching these pods
 */
export class PodWatcherPodsDto {

  constructor(public podWatcher: PodWatcherDto,
              public pods?: PodDto[]) {
  }

}
