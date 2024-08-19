package com.ubs.cpt.infra.domain;

/**
 * Marker interface for all objects holding an {@link EntityId} and therefore encapsulating a UUID via a more typesafe object.
 *
 * @author Amar Pandav
 */
public interface EntityIdAware<E> {

    public EntityId<E> getEntityId();

}
