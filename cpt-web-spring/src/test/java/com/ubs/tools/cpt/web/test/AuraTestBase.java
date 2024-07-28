package com.ubs.tools.cpt.web.test;

import com.ubs.tools.cpt.web.data.aura.AuraTransactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static com.ubs.tools.cpt.web.data.AuraDataSourceConfiguration.AURA_PERSISTENCE_UNIT;

@ContextConfiguration(initializers = AuraCorePostgresqlTestContextInitializer.class)
@TestPropertySource("classpath:application-test.yml")
public class AuraTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(AuraTestBase.class);

    private static final Collection<String> SCRIPTS_TO_EXECUTE = List.of(
        "CREATE-oracle_functions.sql",
        "CREATE-internal_user.sql"
    );

    @PersistenceContext(unitName = AURA_PERSISTENCE_UNIT)
    protected EntityManager auraEntityManager;

    @AuraTransactional
    protected void clearAuraSchema() {
        auraEntityManager.createNativeQuery("DROP SCHEMA public").executeUpdate();
        auraEntityManager.createNativeQuery("CREATE SCHEMA public").executeUpdate();
    }

    @BeforeEach
    @Transactional
    protected void beforeEach() throws IOException {
        clearAuraSchema();
        executeScripts();
    }

    protected void executeScripts() throws IOException {
        for (String scriptFileName : SCRIPTS_TO_EXECUTE) {
            LOG.info("Adding script to batch AURA DB: {}", scriptFileName);
            String resourcePath = "com/ubs/tools/cpt/web/test/aura/sql/" + scriptFileName;
            String scriptContent = resourceContent(resourcePath);

            auraEntityManager.createNativeQuery(scriptContent).executeUpdate();
        }
        LOG.info("Batches executed successfully");
    }


    private static @NotNull String resourceContent(String resourcePath) throws IOException {
        return StreamUtils.copyToString(resourceStream(resourcePath), StandardCharsets.UTF_8);
    }

    private static @Nullable InputStream resourceStream(String resourcePath) {
        return AuraTestBase.class.getClassLoader().getResourceAsStream(resourcePath);
    }
}
