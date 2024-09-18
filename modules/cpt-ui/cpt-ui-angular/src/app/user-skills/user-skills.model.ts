import {UserSkillsLevel} from "./user-skills-level.enum";

export class UserSkillsDto {

  constructor(public skillName: string,
              public skillsLevel: UserSkillsLevel,) {
  }
}
