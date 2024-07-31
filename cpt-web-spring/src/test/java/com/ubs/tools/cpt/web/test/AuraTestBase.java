package com.ubs.tools.cpt.web.test;

import com.ubs.tools.cpt.shared.test.generator.TestDataCreator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.ubs.tools.cpt.web.data.AuraDataSourceConfiguration.AURA_PERSISTENCE_UNIT;

@ActiveProfiles(profiles = "test")
public abstract class AuraTestBase {
    @PersistenceContext(unitName = AURA_PERSISTENCE_UNIT)
    protected EntityManager auraEntityManager;

    protected final TestDataCreator testDataCreator = new TestDataCreator();

    protected abstract void loadTestData();

    @BeforeEach
    @Transactional
    protected void beforeEach() throws IOException {
        testDataCreator.clear();
        loadTestData();
    }
}
