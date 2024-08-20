package com.ubs.cpt.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Amar Pandav
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
@Embeddable
public class AuditInfo implements Serializable {

    public static final class Columns {
        public static final String createdAt = "created_at";
        public static final String createdBy = "created_by";
        public static final String lastModifiedAt = "last_modified_at";
        public static final String lastModifiedBy = "last_modified_by";
    }


    /**
     * Entity was created at.
     */
    @Column(name = Columns.createdAt, nullable = false)
    @ColumnDefault("current_date")
    private LocalDateTime createdAt;

    /**
     * Entity was created by.
     */
    @Column(name = Columns.createdBy, length = FieldConstants.USER_NAME_LEN)
    @ColumnDefault("''")
    private String createdBy;

    /**
     * Entity was last modified at.
     */
    @Column(name = Columns.lastModifiedAt, nullable = true)
    @ColumnDefault("current_date")
    private LocalDateTime lastModifiedAt;

    /**
     * Entity was last modified by.
     */
    @Column(name = Columns.lastModifiedBy, length = FieldConstants.USER_NAME_LEN, nullable = true)
    private String lastModifiedBy;

    public AuditInfo() {

    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

}
