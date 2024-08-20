package com.ubs.cpt.infra.test.base;


import com.ubs.cpt.infra.spring.profiles.ProfileHSQLDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
