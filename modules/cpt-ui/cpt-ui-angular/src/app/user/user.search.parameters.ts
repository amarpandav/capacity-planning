import {UserKey} from "../scheduler/models/user/userKey.model";

export class UserSearchParameters {

    constructor(public userKey?: UserKey, //required, cpt_user.employee_id
                public name?: string, //required, cpt_user.name
                public jobTitle?: string, //cpt_user.role
                public country?: string, /*cpt_user.country*/) {
    }

    /*toJSON = () => {

      const paramsToSet = {
        employeeId: this?.userKey?.employeeId,
        name: this?.name,
        jobTitle: this?.jobTitle,
        country: this?.country
      };
      return paramsToSet;
    };

    toHttpParams(): HttpParams {
      let httpParams = new HttpParams();
      const paramsToSet = {
        employeeId: this?.userKey?.employeeId,
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
