package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.entity.exception.TechnicalException;
import com.ubs.cpt.infra.spring.util.SmoothieTransaction;
import com.ubs.cpt.service.PersistExceptionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Amar Pandav
 */
@Service
public class PersistExceptionServiceImpl implements PersistExceptionService {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SmoothieTransaction
    public String persistException(String requestPath, String loggedInUserName, Throwable exceptionToPersist) {
        TechnicalException e = new TechnicalException(requestPath, loggedInUserName, formatStackTrace(exceptionToPersist));
        em.persist(e);
        String uuid = e.getEntityId().getUuid();
        return uuid;
    }

    private String formatStackTrace(Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        PrintWriter writer = new PrintWriter(stackTrace);

        try {
            exception.printStackTrace(writer);
        } finally {
            writer.close();
        }
        return stackTrace.toString();
    }
}
