package com.ubs.cpt.service;

/**
 * persist service level exception.
 *
 * @author Amar Pandav
 */
public interface PersistExceptionService {

    /**
     * persist exception in Database
     *
     * @param requestPath        current page request path (current page url) where exception occurred
     * @param loggedInUserName   logged in user's userName
     * @param exceptionToPersist exception that occurred
     * @return uuid of TechnicalException
     */
    String persistException(String requestPath, String loggedInUserName, Throwable exceptionToPersist);
}
