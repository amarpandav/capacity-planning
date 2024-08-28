package com.ubs.cpt.infra.spring.jpa;

import jakarta.persistence.EntityManager;

public interface JpaCallbackVoid {
    void doWith(EntityManager em);
}
