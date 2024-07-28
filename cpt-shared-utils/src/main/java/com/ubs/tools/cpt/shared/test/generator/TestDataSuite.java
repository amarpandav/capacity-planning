package com.ubs.tools.cpt.shared.test.generator;

import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.Collections;

public interface TestDataSuite {
    default Collection<TestDataSuite> requiredDataSuites() {
        return Collections.emptyList();
    }

    default String suiteId() {
        return getClass().getName();
    }

    /**
     * Do not use directly. Use via {@link TestDataCreator#persistSuite(TestDataSuite, EntityManager)}
     */
    void persistData(EntityManager em);
}
