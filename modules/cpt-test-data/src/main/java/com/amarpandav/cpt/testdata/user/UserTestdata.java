package com.amarpandav.cpt.testdata.user;

import com.amarpandav.cpt.domain.entity.org.OrgUnit;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;
import com.amarpandav.cpt.testdata.org.OrgUnitTestdata;
/**
 * Testdata for {@link com.amarpandav.cpt.domain.entity.user.User}.
 *
 * @author Amar Pandav
 */
public class UserTestdata {

   private String name;

    private UserKey key;

    private String jobTitle;

    private String country;

    private OrgUnit orgUnit;

    public UserTestdata withName(String name) {
        this.name = name;
        return this;
    }

    public UserTestdata withEmployeeId(UserKey key) {
        this.key = key;
        return this;
    }

    public UserTestdata withJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public UserTestdata withCountry(String country) {
        this.country = country;
        return this;
    }


    public UserTestdata withOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
        return this;
    }

    public User create() {
        return new User(name, key, jobTitle, country, orgUnit);
    }


    public static class SuiteSynthetic extends TestDataSuite<User> {
        private final OrgUnitTestdata.SuiteSynthetic orgUnits = OrgUnitTestdata.suiteSynthetic();

        public User AMAR = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Amar Pandav")
                .withEmployeeId(new UserKey("10000001"))
                .withJobTitle("Engineering Lead")
                .withCountry("Switzerland")
                .withOrgUnit(orgUnits.IDENTITY_AND_ACCESS)
                .create());

        public User LIAM = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Liam Parker")
                .withEmployeeId(new UserKey("10000002"))
                .withJobTitle("Software Developer")
                .withCountry("Switzerland")
                .withOrgUnit(orgUnits.IDENTITY_AND_ACCESS)
                .create());

        public User TOBIAS = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Tobias Berg")
                .withEmployeeId(new UserKey("10000003"))
                .withJobTitle("Software Developer")
                .withCountry("Switzerland")
                .withOrgUnit(orgUnits.CORE_SERVICES)
                .create());

        public User OLIVER = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Oliver Grant")
                .withEmployeeId(new UserKey("10000004"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .withOrgUnit(orgUnits.IDENTITY_AND_ACCESS)
                .create());

        public User KEVIN = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Kevin Lang")
                .withEmployeeId(new UserKey("10000005"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .withOrgUnit(orgUnits.CORE_SERVICES)
                .create());

        public User RACHEL = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Rachel Fox")
                .withEmployeeId(new UserKey("10000006"))
                .withJobTitle("Tech Business Analyst")
                .withCountry("Switzerland")
                .withOrgUnit(orgUnits.BUSINESS_ANALYSIS)
                .create());

        public User TARA = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Tara Stone")
                .withEmployeeId(new UserKey("10000007"))
                .withJobTitle("Project Manager")
                .withCountry("Switzerland")
                .withOrgUnit(orgUnits.PMO)
                .create());

        public User HANNAH = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Hannah Reed")
                .withEmployeeId(new UserKey("10000008"))
                .withJobTitle("Engineering Head")
                .withCountry("UK")
                .withOrgUnit(orgUnits.PLATFORM_ENGINEERING)
                .create());

        public User NT = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Nina Torres")
                .withEmployeeId(new UserKey("10000009"))
                .withJobTitle("Department Head")
                .withCountry("UK")
                .withOrgUnit(orgUnits.DELIVERY_AND_CHANGE)
                .create());


    }

    private static SuiteSynthetic synthetic;

    public static SuiteSynthetic suiteSynthetic() {
        if (synthetic == null) {
            synthetic = new SuiteSynthetic();
        }
        return synthetic;
    }
}
