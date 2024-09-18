import {subscribeOn} from "rxjs";

export enum UserSkillsLevel{
  /*NONE ='NONE',
  BEGINNER = 'BEGINNER',
  INTERMEDIATE_WORKING = 'INTERMEDIATE_WORKING',
  ADVANCED_PRACTITIONER = 'ADVANCED_PRACTITIONER',
  EXPERT_SME = 'EXPERT_SME'*/
  NONE,
  BEGINNER,
  INTERMEDIATE_WORKING,
  ADVANCED_PRACTITIONER,
  EXPERT_SME
}

export function toEnum(str: string) {
  return UserSkillsLevel[str as keyof typeof UserSkillsLevel];
}

export function getUserSkillsLevelKeys(): string[]{
  console.log(Object.keys(UserSkillsLevel));
  return Object.keys(UserSkillsLevel);
}

export function getUserSkillsLevelValue(key: string): string{
  // @ts-ignore
  return UserSkillsLevel[key];
}

export function getUserSkillsLevelKey(value: UserSkillsLevel): string{
  // @ts-ignore
  return UserSkillsLevel[value];
}

export function isNone(availability: UserSkillsLevel) {
  return UserSkillsLevel.NONE === availability;
}

export function isBeginner(availability: UserSkillsLevel) {
  return UserSkillsLevel.BEGINNER === availability;
}

export function isIntermediateWorking(availability: UserSkillsLevel) {
  return UserSkillsLevel.INTERMEDIATE_WORKING === availability;
}
export function isAdvancedPractitioner(availability: UserSkillsLevel) {
  return UserSkillsLevel.ADVANCED_PRACTITIONER === availability;
}
export function isExpertSME(availability: UserSkillsLevel) {
  return UserSkillsLevel.EXPERT_SME === availability;
}
