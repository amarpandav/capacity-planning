package com.ubs.tools.cpt.web.data.aura;

import com.ubs.tools.cpt.shared.test.generator.TestDataSuite;
import com.ubs.tools.cpt.web.data.aura.entity.InternalUser;
import jakarta.persistence.EntityManager;

import static com.ubs.tools.cpt.web.test.AuraUuidGenerator.generateAuraUuid;

public class InternalUserSuiteGeneric implements TestDataSuite {
    public final InternalUser JORDAN_HAMMOND = InternalUser.builder()
                                                           .uuid(generateAuraUuid())
                                                           .pid("G01")
                                                           .fullName("Jordan Hammond")
                                                           .email("jordan.hammond@ubs.com")
                                                           .build();

    public final InternalUser PETER_DIAMOND = InternalUser.builder()
                                                          .uuid(generateAuraUuid())
                                                          .pid("G02")
                                                          .fullName("Peter Diamond")
                                                          .email("peter.diamond@ubs.com")
                                                          .build();

    public final InternalUser GUSTAV_VOLKER = InternalUser.builder()
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
