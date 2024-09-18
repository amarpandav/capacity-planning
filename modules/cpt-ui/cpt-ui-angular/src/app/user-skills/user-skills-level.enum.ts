export enum UserSkillsLevel{
  NONE ='NONE',
  BEGINNER = 'BEGINNER',
  INTERMEDIATE_WORKING = 'INTERMEDIATE_WORKING',
  ADVANCED_PRACTITIONER = 'ADVANCED_PRACTITIONER',
  EXPERT_SME = 'EXPERT_SME'
}

export function toEnum(str: string) {
  return UserSkillsLevel[str as keyof typeof UserSkillsLevel];
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
