export enum PodMemberRole{
  JAVA_DEVELOPER = 'JD',
  NET_DEVELOPER = 'ND',
  LEAD_DEVELOPER = 'LD',
  TESTER = 'TE',
  POD_LEAD = 'PL',
  SOLUTIONS_ARCHITECT = 'SA',
  BUSINESS_ALALYSTS = 'BA',
  PRODUCT_OWNER = 'PO',
  UI_UX = 'UX'
}

export function toEnum(roleAsStr: string) {
  return PodMemberRole[roleAsStr as keyof typeof PodMemberRole];
}

export function isJavaDeveloper(role: PodMemberRole) {
  return PodMemberRole.JAVA_DEVELOPER === role;
}

export function isNetDeveloper(role: PodMemberRole) {
  return PodMemberRole.NET_DEVELOPER === role;
}

export function isLeadDeveloper(role: PodMemberRole) {
  return PodMemberRole.LEAD_DEVELOPER === role;
}

export function isTester(role: PodMemberRole) {
  return PodMemberRole.TESTER === role;
}

export function isPodLead(role: PodMemberRole) {
  return PodMemberRole.POD_LEAD === role;
}

export function isSA(role: PodMemberRole) {
  return PodMemberRole.SOLUTIONS_ARCHITECT === role;
}

export function isBA(role: PodMemberRole) {
  return PodMemberRole.BUSINESS_ALALYSTS === role;
}

export function isPO(role: PodMemberRole) {
  return PodMemberRole.PRODUCT_OWNER === role;
}

export function isUIUX(role: PodMemberRole) {
  return PodMemberRole.UI_UX === role;
}

