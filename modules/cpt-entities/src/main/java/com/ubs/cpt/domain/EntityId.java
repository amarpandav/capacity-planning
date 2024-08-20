package com.ubs.cpt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

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
        if (StringUtils.isNotBlank(uuid)) {
            return new EntityId<E>(uuid);
        }
        return null;
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
