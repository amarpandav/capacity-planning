package com.ubs.cpt.infra.test.base;


import com.google.common.collect.Lists;
import com.ubs.cpt.infra.domain.BaseEntity;
import com.ubs.cpt.infra.domain.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Base class for test data suites. Use {@link #register(BaseEntity)} to enable auto collecting and persisting.
 * Takes care of all created entities, persisting, and some other helpers.
 *
 * @author Amar Pandav
 */
public abstract class TestDataSuite<E extends BaseEntity> {

    private static final Logger LOG = LoggerFactory.getLogger(TestDataSuite.class);

    private List<E> entities = Lists.newArrayList();

    /**
     * get all registered entities of this suite
     */
    public List<E> allEntities() {
        return entities;
    }

    /**
     * Persists all registered entities to the passed em
     */
    public void persistTo(EntityManager em) {
        LOG.debug("persisting {} entities for {}", entities.size(), getClass().getName());

        for (E entity : entities) {
            em.persist(entity);
        }
        em.flush();
    }

    /**
     * Registers the entity in allEntities() list for persistTo
     */
    protected E register(E entity) {
        entities.add(entity);
        return entity;
    }

    protected List<E> register(List<E> entities1) {
        entities.addAll(entities1);
        return entities;
    }

    /**
     * Finds an entity using it`s uuid out of the registered ones.
     */
    public E getEntity(EntityId<E> entityId) {
        for (E entity : entities) {
            if (entity.getEntityId().equals(entityId)) {
                return entity;
            }
        }
        return null;
    }
}
