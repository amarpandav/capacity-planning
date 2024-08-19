package com.ubs.cpt.shared.test.generator;

import jakarta.persistence.EntityManager;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class TestDataCreator {
    private final Set<String> persistedSuites = new ConcurrentSkipListSet<>();

    public void persistSuite(TestDataSuite suite, EntityManager em) {
        if (persistedSuites.contains(suite.suiteId())) {
            return;
        }

        suite.requiredDataSuites().forEach(s -> persistSuite(s, em));

        try {
            suite.persistData(em);
            em.flush();
            persistedSuites.add(suite.suiteId());
        } catch (Exception ex) {
            throw new IllegalStateException("Could not persist suite " + suite.suiteId(), ex);
        }
    }

    public void clear() {
        persistedSuites.clear();
    }
}
