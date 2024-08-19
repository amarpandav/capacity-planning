package com.ubs.cpt.web.test;

import com.ubs.cpt.shared.test.generator.TestDataCreator;
import com.ubs.cpt.web.data.aura.AuraTransactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;

import static com.ubs.cpt.web.data.aura.AuraDataSourceConfiguration.AURA_PERSISTENCE_UNIT;

@ActiveProfiles(profiles = "test")
public abstract class AuraTestBase {
    @PersistenceContext(unitName = AURA_PERSISTENCE_UNIT)
    protected EntityManager auraEntityManager;

    protected final TestDataCreator testDataCreator = new TestDataCreator();

    protected abstract void loadTestData() throws Exception;

    @BeforeEach
    @AuraTransactional
    protected void beforeEach() throws Exception {
        testDataCreator.clear();
        loadTestData();
    }
}
