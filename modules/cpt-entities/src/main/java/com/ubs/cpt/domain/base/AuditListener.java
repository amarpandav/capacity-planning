package com.ubs.cpt.domain.base;

import com.ubs.cpt.domain.BaseEntity;
import jakarta.persistence.PrePersist;

public class AuditListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.prePersist();
    }
}
