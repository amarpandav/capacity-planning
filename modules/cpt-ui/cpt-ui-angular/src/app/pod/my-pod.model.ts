import {EntityId} from "../scheduler/models/entityId.model";

export class MyPodDto {

    constructor(public userEntityId: EntityId<string>,
                public myPodInfo: MyPodInfoDto) {
    }
}

export class MyPodInfoDto {
    constructor(public entityId: EntityId<string>,
                public name: string) {
    }
}