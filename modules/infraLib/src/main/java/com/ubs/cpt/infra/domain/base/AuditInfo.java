package com.ubs.cpt.infra.domain.base;

import com.ubs.cpt.infra.datetime.CoreDateTimeUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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
    @Type(type = "com.ubs.cpt.infra.hibernate.DateTimeType")
    @Column(name = Columns.createdAt, nullable = false)
    private DateTime createdAt;

    /**
     * Entity was created by.
     */
    @Column(name = Columns.createdBy, length = FieldConstants.USER_NAME_LEN, nullable = false)
    private String createdBy;

    /**
     * Entity was last modified at.
     */
    @Type(type = "com.ubs.cpt.infra.hibernate.DateTimeType")
    @Column(name = Columns.lastModifiedAt, nullable = true)
    private DateTime lastModifiedAt;

    /**
     * Entity was last modified by.
     */
    @Column(name = Columns.lastModifiedBy, length = FieldConstants.USER_NAME_LEN, nullable = true)
    private String lastModifiedBy;

    public AuditInfo() {

    }

    /**
     * Called by JPA container before sql insert.
     */
    public void prePersist() {
        this.createdAt = CoreDateTimeUtils.dateTimeWithTimeZoneJoda();
        this.createdBy = getUserName();
    }

    /**
     * Called by JPA container before sql update.
     */
    public void preUpdate() {
        //leaves the option open for overriding the last modified by, e.g. setting it on every change or just keeping the last set value (e.g. background running jobs don't always need to change this)
        this.lastModifiedAt = CoreDateTimeUtils.dateTimeWithTimeZoneJoda();
        this.lastModifiedBy = getUserName();
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? "unknown" : (String) authentication.getPrincipal();
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

}
