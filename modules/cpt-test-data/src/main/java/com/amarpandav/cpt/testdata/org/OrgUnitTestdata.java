package com.amarpandav.cpt.testdata.org;

import com.amarpandav.cpt.domain.entity.org.OrgUnit;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;

/**
 * Testdata for {@link com.amarpandav.cpt.domain.entity.org.OrgUnit}.
 * Parents are registered before their children so they are persisted first.
 *
 * @author Amar Pandav
 */
public class OrgUnitTestdata {

    private String orgUnitCode;

    private String orgUnitName;

    private OrgUnit parent;

    public OrgUnitTestdata withOrgUnitCode(String orgUnitCode) {
        this.orgUnitCode = orgUnitCode;
        return this;
    }

    public OrgUnitTestdata withOrgUnitName(String orgUnitName) {
        this.orgUnitName = orgUnitName;
        return this;
    }

    public OrgUnitTestdata withParent(OrgUnit parent) {
        this.parent = parent;
        return this;
    }

    public OrgUnit create() {
        return new OrgUnit(orgUnitCode, orgUnitName, parent);
    }


    public static class SuiteSynthetic extends TestDataSuite<OrgUnit> {
        // Division
        public OrgUnit TECHNOLOGY = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC")
                .withOrgUnitName("Technology")
                .create());

        // Departments
        public OrgUnit PLATFORM_ENGINEERING = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC-PLT")
                .withOrgUnitName("Platform Engineering")
                .withParent(TECHNOLOGY)
                .create());

        public OrgUnit DELIVERY_AND_CHANGE = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC-DLV")
                .withOrgUnitName("Delivery & Change")
                .withParent(TECHNOLOGY)
                .create());

        public OrgUnit QUALITY_ENGINEERING = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC-QA")
                .withOrgUnitName("Quality Engineering")
                .withParent(TECHNOLOGY)
                .create());

        // Teams
        public OrgUnit IDENTITY_AND_ACCESS = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC-PLT-01")
                .withOrgUnitName("Identity & Access Services")
                .withParent(PLATFORM_ENGINEERING)
                .create());

        public OrgUnit CORE_SERVICES = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC-PLT-02")
                .withOrgUnitName("Core Services")
                .withParent(PLATFORM_ENGINEERING)
                .create());

        public OrgUnit PMO = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC-DLV-01")
                .withOrgUnitName("Project Management Office")
                .withParent(DELIVERY_AND_CHANGE)
                .create());

        public OrgUnit BUSINESS_ANALYSIS = register(new OrgUnitTestdata()
                .withOrgUnitCode("TEC-DLV-02")
                .withOrgUnitName("Business Analysis")
                .withParent(DELIVERY_AND_CHANGE)
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
