package com.ubs.cpt.web.data.aura;

import com.ubs.cpt.shared.test.generator.TestDataSuite;
import com.ubs.cpt.web.data.aura.entity.AuraInternalUser;
import jakarta.persistence.EntityManager;

import static com.ubs.cpt.web.test.AuraUuidGenerator.generateAuraUuid;

public class InternalUserSuiteGeneric implements TestDataSuite {
    public final AuraInternalUser JORDAN_HAMMOND = AuraInternalUser.builder()
                                                                   .uuid(generateAuraUuid())
                                                                   .pid("G01")
                                                                   .fullName("Jordan Hammond")
                                                                   .email("jordan.hammond@ubs.com")
                                                                   .build();

    public final AuraInternalUser PETER_DIAMOND = AuraInternalUser.builder()
                                                                  .uuid(generateAuraUuid())
                                                                  .pid("G02")
                                                                  .fullName("Peter Diamond")
                                                                  .email("peter.diamond@ubs.com")
                                                                  .build();

    public final AuraInternalUser GUSTAV_VOLKER = AuraInternalUser.builder()
                                                                  .uuid(generateAuraUuid())
                                                                  .pid("G03")
                                                                  .fullName("Gustav Volker")
                                                                  .email("gustav.volker@ubs.com")
                                                                  .build();

    @Override
    public void persistData(EntityManager em) {
        em.persist(JORDAN_HAMMOND);
        em.persist(PETER_DIAMOND);
        em.persist(GUSTAV_VOLKER);
    }
}
