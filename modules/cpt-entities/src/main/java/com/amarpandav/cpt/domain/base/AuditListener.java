package com.amarpandav.cpt.domain.base;

import com.amarpandav.cpt.domain.BaseEntity;
import jakarta.persistence.PrePersist;

public class AuditListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.prePersist();
    }
}
