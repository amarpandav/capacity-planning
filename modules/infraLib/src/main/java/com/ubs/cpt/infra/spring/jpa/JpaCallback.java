package com.ubs.cpt.infra.spring.jpa;

import javax.persistence.EntityManager;

public interface JpaCallback<T> {
    T doWith(EntityManager em);
}
