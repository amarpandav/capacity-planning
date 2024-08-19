package com.ubs.cpt.infra.test.util;

import com.smoothie.infra.spring.profiles.SpringProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Connection;

/**
 * Sql Utils for testing purposes.
 *
 * @author Amar Pandav
 */
public class SqlTestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SqlTestUtils.class);

    private SqlTestUtils() {
    }

    public static void truncatePublicSchema(EntityManager em) {
        if (SpringProfiles.isHSQLDB()) {
            em.getTransaction().begin();

            /* ATTENTION: this can lead to a deadlock if there is ANY TRANSACTION still open on this datasource.
            *  So if used in a @Transactional test case, the trancation has to be done in @AfterTransaction instead of @After.
            *  It is hard auto check this, because we would need the used transaction manager for this.
            *  The NO CHECK addition ignores foreign key constraints and makes truncate even faster. */
            Query query = em.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT NO CHECK");
            query.executeUpdate();
            LOG.info("truncated schema public@{}");

            query = em.createNativeQuery("drop table dual if exists");
            query.executeUpdate();
            em.getTransaction().commit();
        }
    }

    public static void executeSqlScripts(Connection connection, Class<?> loadResourcesFrom, String... scriptResourceNames) {
        ClassPathResource[] sqls = new ClassPathResource[scriptResourceNames.length];
        int i = 0;
        for (String resourceName : scriptResourceNames) {
            sqls[i++] = new ClassPathResource(resourceName, loadResourcesFrom);
        }
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setIgnoreFailedDrops(true);
        populator.setScripts(sqls);
        populator.populate(connection);
    }
}
