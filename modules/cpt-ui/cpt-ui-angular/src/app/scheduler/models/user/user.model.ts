/**
 * Model representing cpt_user DB table.
 * Model to define employees who are registered to capacity planning tool.
 * Later we can also load all employees here from HR database.
 */
export class UserDto {

  constructor(public uuid: string, //required, cpt_user.uuid
              public gpin: string, //required, cpt_user.gpin
              public name: string, //required, cpt_user.name
              public jobTitle?: string, //cpt_user.role
              public country?: string, /*cpt_user.country*/) {
  }
}
