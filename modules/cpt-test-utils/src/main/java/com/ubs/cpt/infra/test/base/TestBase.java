package com.ubs.cpt.infra.test.base;


import com.ubs.cpt.infra.datasource.Datasource;
import com.ubs.cpt.infra.datetime.DateTimeService;
import com.ubs.cpt.infra.datetime.DelegatingSupplierDateTimeService;
import com.ubs.cpt.infra.datetime.FixedDateTimeService;
import com.ubs.cpt.infra.spring.profiles.ProfileHSQLDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Amar Pandav
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class}))
*/
@SpringBootTest
@ComponentScan(basePackages = "com.ubs.cpt")
@ActiveProfiles(value = {ProfileHSQLDB.VALUE, "test"}) // The way to activate the profile
@Import(TestBase.Configuration.class)
public abstract class TestBase {
    protected void setTestDateTime(LocalDateTime nowDateTime) {
        this.nowDateTime.set(nowDateTime);
    }

    protected LocalDateTime getTestDateTime() {
        return nowDateTime.get();
    }


    @Autowired
    TestDataCreator testDataCreator;

    @Autowired
    @Qualifier(Configuration.TEST_DATE_TIME)
    AtomicReference<LocalDateTime> nowDateTime;

    //    @After
    public void after() throws Exception {
        //After each test re-fresh the test data for consistent test data.
        testDataCreator.refreshTestData();
    }

    public static class Configuration {
        public static final String TEST_DATE_TIME = "testDateTime";

        private AtomicReference<LocalDateTime> testDateTime = new AtomicReference<>(LocalDateTime.of(2024, 8, 1, 12, 0));

        @Bean(TEST_DATE_TIME)
        public AtomicReference<LocalDateTime> testDateTime() {
            return testDateTime;
        }

        @Bean
        public DateTimeService getDateTimeService() {
            return new DelegatingSupplierDateTimeService(() -> FixedDateTimeService.ofLocalDateTime(testDateTime.get()));
        }
    }
}
