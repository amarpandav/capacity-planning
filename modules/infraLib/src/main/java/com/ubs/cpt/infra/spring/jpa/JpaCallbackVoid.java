package com.ubs.cpt.infra.spring.jpa;

import javax.persistence.EntityManager;

public interface JpaCallbackVoid {
    void doWith(EntityManager em);
}
