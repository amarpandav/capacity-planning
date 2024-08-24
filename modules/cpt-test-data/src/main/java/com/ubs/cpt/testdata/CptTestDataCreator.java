package com.ubs.cpt.testdata;

import com.ubs.cpt.infra.spring.jpa.JpaCallbackVoid;
import com.ubs.cpt.infra.spring.jpa.JpaHelper;
import com.ubs.cpt.infra.spring.profiles.SpringProfiles;
import com.ubs.cpt.infra.test.base.TestDataCreator;
import com.ubs.cpt.infra.test.util.SqlTestUtils;
import com.ubs.cpt.infra.util.JpaUtils;
import com.ubs.cpt.testdata.availability.AvailabilityTestdata;
import com.ubs.cpt.testdata.pod.PodMemberTestdata;
import com.ubs.cpt.testdata.pod.PodTestdata;
import com.ubs.cpt.testdata.pod.PodWatcherTestdata;
import com.ubs.cpt.testdata.user.UserBookedCapacityTestdata;
import com.ubs.cpt.testdata.user.UserTestdata;
import com.ubs.cpt.testdata.user.UserUnAvailableCapacityTestdata;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Creates test data.
 *
 * @author Amar Pandav
 */
@Slf4j
@Service
public class CptTestDataCreator implements TestDataCreator, ApplicationListener<ApplicationContextEvent> {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    DataSource dataSource;

    private boolean refreshTestData = true;


    @Override
    public void createTestData() {
        if (SpringProfiles.isHSQLDB() && refreshTestData) {
            if ("true".equals(System.getProperty("showHSQLConsole"))) {
                //Show HSQL DB Console ?
                try {
                    log.info("Showing DB consle");
                    DatabaseManagerSwing.main(new String[]{"--url", dataSource.getConnection().getMetaData().getURL() +";sql.syntax_mys=true", "--noexit"});
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            //We can even annotate this method with @Transactional but it will not work because Transactions aren't available
            // in the init method, because no (transactional) proxy is created yet.
            //Hence handle it manually.

            new JpaHelper(emf).doTransactional(new JpaCallbackVoid() {
                @Override
                public void doWith(EntityManager em) {
                    insertTestData(em);
                }
            });

            refreshTestData = false; //Test data got created, set this flag to false in-order to avoid duplicate data creation
        }

    }

    @Override
    public void refreshTestData() {
        log.info("Refreshing DB test data");
        refreshTestData = true;
        deleteOldTestData();
        createTestData();
    }

    private void insertTestData(EntityManager em) {
        log.info("Inserting DB test data");
        executeAdditionalSqlScripts(em);

        UserTestdata.suiteSynthetic().persistTo(em);
        AvailabilityTestdata.suiteSynthetic().persistTo(em);
        PodTestdata.suiteSynthetic().persistTo(em);
        PodMemberTestdata.suiteSynthetic().persistTo(em);
        PodWatcherTestdata.suiteSynthetic().persistTo(em);
        UserBookedCapacityTestdata.suiteSynthetic().persistTo(em);
        UserUnAvailableCapacityTestdata.suiteSynthetic().persistTo(em);
    }

    private void executeAdditionalSqlScripts(EntityManager em) {

        String[] scripts = {"/testdata/cpt-hsql.sql"};
        for (String script : scripts) {
            SqlTestUtils.executeSqlScripts(JpaUtils.getConnection(em), getClass(), script);
        }
    }

    private void deleteOldTestData() {
        // try cleanup
        try {
            if (SpringProfiles.isHSQLDB() && refreshTestData) {
                new JpaHelper(emf).doManaged(SqlTestUtils::truncatePublicSchema);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Filed to cleanup databases and/or Testdata classes!", e);
        }

    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        createTestData();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}

