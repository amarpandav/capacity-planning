/**
 * Model representing audit info reused across many DB tables.
 */
export class AuditInfoDto {


  constructor(public createdAt?: Date | null,
              public createdByGPIN?: string| null,
              public createdBy?: string| null,
              public lastModifiedAt?: Date | null,
              public lastModifiedByGPIN?: string | null,
              public lastModifiedBy?: string| null,
              public lastCertifiedAt?: Date | null,
              public lastCertifiedByGPIN?: string| null,
              public lastCertifiedBy?: string| null) {
  }

}
