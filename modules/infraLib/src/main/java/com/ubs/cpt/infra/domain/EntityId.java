package com.ubs.cpt.infra.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;

/**
 * @author Amar Pandav
 */
@Embeddable
public class EntityId<T> implements Serializable {

    @Column(name = "uuid", length = 32)
    private String uuid;

    public EntityId(String uuid) {
        this.uuid = uuid;
    }

    public EntityId() {
        //default constructor for JPA
    }

    public static <T> EntityId<T> create() {
        return new EntityId<T>(UUIDGenerator.next());
    }

    /**
     * Factory method to save the double typing in some cases
     */
    public static <E> EntityId<E> fromUuid(String uuid) {
        if (isNotEmpty(uuid)) {
            return new EntityId<E>(uuid);
        }
        return null;
    }

    public static <E> List<EntityId<E>> fromUuids(List<String> uuids) {
        if (uuids == null) return Collections.emptyList();

        List<EntityId<E>> entityIds = newArrayListWithCapacity(uuids.size());
        for (String uuid : uuids) {
            if (uuid != null) {
                entityIds.add(EntityId.<E>fromUuid(uuid));
            }
        }
        return entityIds;
    }

    public static <E> List<String> toUuids(List<EntityId<E>> entityIds) {
        if (entityIds == null) return Collections.emptyList();

        List<String> uuids = newArrayListWithCapacity(entityIds.size());
        for (EntityId<E> entityId : entityIds) {
            if (entityId != null) {
                uuids.add(entityId.getUuid());
            }
        }
        return uuids;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityId entityId = (EntityId) o;

        if (uuid != null ? !uuid.equals(entityId.uuid) : entityId.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EntityId{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
