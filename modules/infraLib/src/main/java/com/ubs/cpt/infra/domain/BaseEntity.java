package com.ubs.cpt.infra.domain;


import com.ubs.cpt.infra.domain.base.AuditInfo;
import com.ubs.cpt.infra.domain.base.AuditListener;
import com.ubs.cpt.infra.domain.base.Auditable;

import javax.persistence.*;

/**
 * Base type for all entities.
 * <p/>
 *
 * @author Amar Pandav
 */
@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
@MappedSuperclass
@EntityListeners({AuditListener.class})
public abstract class BaseEntity<E> implements Auditable, EntityIdAware<E> {

    public static final String UUID = "uuid";
    public static final String VERSION = "version";


    @EmbeddedId
    private EntityId<E> entityId;

    /**
     * Technical unique key of the row.
     */
    @Version
    @Column(name = VERSION, nullable = false, columnDefinition = "int default 0")
    //@Default(sqlExpression = "0")
    private Long version;

    @Embedded
    private AuditInfo auditInfo;

    /**
     * Default constructor.
     */
    protected BaseEntity() {
    }

    public AuditInfo getAuditInfo() {
        if (auditInfo == null) {
            auditInfo = new AuditInfo();
        }
        return auditInfo;
    }

    public EntityId<E> getEntityId() {
        if (entityId == null) {
            entityId = EntityId.create();
        }
        return entityId;
    }

    @PrePersist
    public void prePersist() {
        if (entityId == null) {
            entityId = EntityId.create();
        }
    }

    public void setEntityId(EntityId<E> entityId) {
        this.entityId = entityId;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public boolean isTransient() {
        return version == null;
    }

    /**
     * {@inheritDoc}
     *
     * @param o other entity
     * @return true if the given entity is equal (actually the same entity)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(getClass().isAssignableFrom(o.getClass()) || o.getClass().isAssignableFrom(getClass()))) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return getEntityId().equals(that.getEntityId());
    }

    /**
     * {@inheritDoc}
     *
     * @return hash code of this entity
     */
    @Override
    public int hashCode() {
        return getEntityId().getUuid().hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '{' + getAttributesString() + '}';
    }

    protected String getAttributesString() {
        return "uuid=" + getEntityId().getUuid() +
                ", version=" + version;
    }

}
