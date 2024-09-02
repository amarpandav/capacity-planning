import {UserKey} from "../scheduler/models/user/userKey.model";

export class UserSearchParameters {

    constructor(public userKey?: UserKey, //required, cpt_user.gpin
                public name?: string, //required, cpt_user.name
                public jobTitle?: string, //cpt_user.role
                public country?: string, /*cpt_user.country*/) {
    }

    /*toJSON = () => {

      const paramsToSet = {
        gpin: this?.userKey?.gpin,
        name: this?.name,
        jobTitle: this?.jobTitle,
        country: this?.country
      };
      return paramsToSet;
    };

    toHttpParams(): HttpParams {
      let httpParams = new HttpParams();
      const paramsToSet = {
        gpin: this?.userKey?.gpin,
        name: this?.name,
        jobTitle: this?.jobTitle,
        country: this?.country
      };

      Object.entries(paramsToSet).forEach(([key, value]) => {
        if (value) {
          httpParams = httpParams.set(key, value);
        }
      });
      return httpParams;
    }*/

}
