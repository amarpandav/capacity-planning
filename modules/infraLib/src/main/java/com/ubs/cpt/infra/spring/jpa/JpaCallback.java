package com.ubs.cpt.infra.spring.jpa;

import jakarta.persistence.EntityManager;

public interface JpaCallback<T> {
    T doWith(EntityManager em);
}
