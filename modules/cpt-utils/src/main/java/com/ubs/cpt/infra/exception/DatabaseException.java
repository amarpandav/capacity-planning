package com.ubs.cpt.infra.exception;

import org.hibernate.JDBCException;

/**
 * <p>Utility class that is supposed to wrap PersistenceExceptions. It includes more information in the error log if
 * you use this one rather than throwing another PersistenceException (or anything like that).</p>
 *
 * @author Amar Pandav
 */
public class DatabaseException extends RuntimeException {

    // ------------------------------------------ Constructors

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message + enrichPersistenceException(cause), cause);
    }

    // ------------------------------------------ Utility methods

    private static String enrichPersistenceException(Throwable cause) {
        String queryInformation = "";
        if ((cause.getCause() instanceof JDBCException)) {
            JDBCException jdbcException = (JDBCException) cause.getCause();
            queryInformation = " It has been translated into the native SQL query '" + jdbcException.getSQL() + "'.";
        }

        return queryInformation;
    }

}
