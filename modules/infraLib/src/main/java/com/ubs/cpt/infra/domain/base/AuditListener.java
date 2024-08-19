package com.ubs.cpt.infra.domain.base;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author Amar Pandav
 */
@SuppressWarnings({"UnusedDeclaration", "JpaEntityListenerInspection"})
public class AuditListener {
    //private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(BaseEntityListener.class);

    @PrePersist
    public void prePersist(Auditable auditable) {
        auditable.getAuditInfo().prePersist();
    }

    @PreUpdate
    public void preUpdate(Auditable auditable) {
        auditable.getAuditInfo().preUpdate();
    }
}
