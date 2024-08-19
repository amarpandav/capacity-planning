package com.ubs.cpt.infra.spring.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class JpaHelper {

    private final EntityManagerFactory emf;

    public JpaHelper(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public <T> T doManaged(JpaCallback<T> callback) {
        T result;
        EntityManager em = emf.createEntityManager();
        try {
            result = callback.doWith(em);
        } finally {
            em.close();
        }
        return result;
    }

    public void doManaged(JpaCallbackVoid callback) {
        EntityManager em = emf.createEntityManager();
        try {
            callback.doWith(em);
        } finally {
            em.close();
        }
    }

    public <T> T doTransactional(JpaCallback<T> callback) {
        T result;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            result = callback.doWith(em);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        return result;
    }

    public void doTransactional(JpaCallbackVoid callback) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            callback.doWith(em);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

}
