package com.ubs.cpt.infra.test.base;


import com.ubs.cpt.infra.spring.config.AppConfig;
import com.ubs.cpt.infra.spring.profiles.ProfileHSQLDB;
import com.ubs.cpt.infra.test.config.TestDataConfig;
//import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * @author Amar Pandav
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class}))
*/
@SpringBootTest
@ActiveProfiles(value = {ProfileHSQLDB.VALUE}) // The way to activate the profile
public abstract class TestBase {

    @Autowired
    TestDataCreator testDataCreator;

//    @After
    public void after() throws Exception {
        //After each test re-fresh the test data for consistent test data.
        testDataCreator.refreshTestData();
    }
}
