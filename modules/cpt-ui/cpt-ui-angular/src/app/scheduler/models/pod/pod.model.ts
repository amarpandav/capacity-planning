import {EntityId} from "../entityId.model";

/**
 * Model representing cpt_pod DB table.
 */
export class PodDto {

    constructor(public entityId:  EntityId<string>, //mandatory
                public podName: string,//mandatory
                public podShortName: string,//mandatory
                public podStyleClass: string,//mandatory
                public podDescription: string/*mandatory*/) {
    }

}
