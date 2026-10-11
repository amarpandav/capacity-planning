/**
 * Model representing audit info reused across many DB tables.
 */
export class AuditInfoDto {


  constructor(public createdAt?: Date | null,
              public createdByEmployeeId?: string| null,
              public createdBy?: string| null,
              public lastModifiedAt?: Date | null,
              public lastModifiedByEmployeeId?: string | null,
              public lastModifiedBy?: string| null,
              public lastCertifiedAt?: Date | null,
              public lastCertifiedByEmployeeId?: string| null,
              public lastCertifiedBy?: string| null) {
  }

}
