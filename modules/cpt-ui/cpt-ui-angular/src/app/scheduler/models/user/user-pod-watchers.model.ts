import {PodDto} from "../pod/pod.model";
import {UserDto} from "./user.model";

/**
 * podMember is part of these pods
 */
export class UserPodWatchersDto {

    constructor(public user: UserDto,
                public userPodWatcherDetails: UserPodWatcherDetailsDto[]) {
    }

}

export class UserPodWatcherDetailsDto {

    constructor(public podWatcherUuid: string, //PodWatcher.uuid
                public pod: PodDto) {
    }

}
