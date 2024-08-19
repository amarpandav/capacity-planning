package com.ubs.cpt.domain.entity.exception;

import com.ubs.cpt.infra.domain.BaseEntity;
import com.ubs.cpt.infra.domain.base.FieldConstants;

import jakarta.persistence.*;

/**
 * @author Amar Pandav
 */
@Entity
@Table(
        name = TechnicalException.TABLE_NAME
)
public class TechnicalException extends BaseEntity<TechnicalException> {

    public static final String TABLE_NAME = "technical_exception";

    public static final class Columns {
        public static final String requestPath = "request_path";
        public static final String stackTrace = "stack_trace";
        public static final String userName = "user_name";
    }

    @Column(name = Columns.requestPath, nullable = false)
    private String requestPath;

    @Column(name = Columns.userName, length = FieldConstants.USER_NAME_LEN, nullable = false)
    private String userName;

    @Column(name = Columns.stackTrace, length = FieldConstants.STACK_TRACE_LEN)
    @Lob
    private String stackTrace;

    protected TechnicalException() {// required by JPA
    }

    public TechnicalException(String requestPath, String userName, String stackTrace) {
        this.requestPath = requestPath;
        this.userName = userName;
        this.stackTrace = stackTrace;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getUserName() {
        return userName;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
