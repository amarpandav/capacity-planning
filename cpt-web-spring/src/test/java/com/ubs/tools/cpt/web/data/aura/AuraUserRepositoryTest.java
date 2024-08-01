package com.ubs.tools.cpt.web.data.aura;

import com.ubs.tools.cpt.web.data.aura.entity.AuraInternalUser;
import com.ubs.tools.cpt.web.test.AuraTestBase;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AuraUserRepositoryTest extends AuraTestBase {
    @Autowired
    AuraUserRepository auraUserRepository;

    private final InternalUserSuiteGeneric internalUserSuiteGeneric = new InternalUserSuiteGeneric();

    @Test
    @AuraTransactional
    void findUsers_byUuid() {
        AuraInternalUser jordanHammond = internalUserSuiteGeneric.JORDAN_HAMMOND;
        AuraInternalUser peterDiamond = internalUserSuiteGeneric.PETER_DIAMOND;

        auraUserRepository.findUsers(jordanHammond.getUuid(), null, null)
                          .findFirst()
                          .ifPresentOrElse(
                              user -> {
                                  assertEquals(jordanHammond.getFullName(), user.fullName());
                                  assertEquals(jordanHammond.getEmail(), user.email());
                              },
                              () -> {
                                  throw new AssertionError("User not found");
                              }
                          );
        auraUserRepository.findUsers(peterDiamond.getUuid(), null, null)
                          .findFirst()
                          .ifPresentOrElse(
                              user -> {
                                  assertEquals(peterDiamond.getFullName(), user.fullName());
                                  assertEquals(peterDiamond.getEmail(), user.email());
                              },
                              () -> {
                                  throw new AssertionError("User not found");
                              }
                          );
    }

    @Test
    @AuraTransactional
    void findUsers_by_FullName() {
        AuraInternalUser jordanHammond = internalUserSuiteGeneric.JORDAN_HAMMOND;
        AuraInternalUser peterDiamond = internalUserSuiteGeneric.PETER_DIAMOND;
        AuraInternalUser gustavVolker = internalUserSuiteGeneric.GUSTAV_VOLKER;

        var users = auraUserRepository.findUsers(null, "moNd", null).toList();

        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals(jordanHammond.getUuid()), "Jordan Hammond user found"));
        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals(peterDiamond.getUuid()), "Peter Diamond user found"));
        assertThatCollection(users).noneMatch(u -> u.uuid().equals(gustavVolker.getUuid()));
    }

    @Test
    @AuraTransactional
    void findUsers_by_Email() {
        AuraInternalUser jordanHammond = internalUserSuiteGeneric.JORDAN_HAMMOND;
        AuraInternalUser peterDiamond = internalUserSuiteGeneric.PETER_DIAMOND;
        AuraInternalUser gustavVolker = internalUserSuiteGeneric.GUSTAV_VOLKER;

        var users = auraUserRepository.findUsers(null, null, "nd@ubs").toList();

        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals(jordanHammond.getUuid()), "Jordan Hammond user found"));
        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals(peterDiamond.getUuid()), "Peter Diamond user found"));
        assertThatCollection(users).noneMatch(u -> u.uuid().equals(gustavVolker.getUuid()));
    }

    @Test
    @AuraTransactional
    void findUsers_by_Email_and_FullName() {
        AuraInternalUser jordanHammond = internalUserSuiteGeneric.JORDAN_HAMMOND;
        AuraInternalUser peterDiamond = internalUserSuiteGeneric.PETER_DIAMOND;
        AuraInternalUser gustavVolker = internalUserSuiteGeneric.GUSTAV_VOLKER;

        var users = auraUserRepository.findUsers(null, "av", "O").toList();

        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals(gustavVolker.getUuid()), "First user found"));
        assertThatCollection(users).noneMatch(u -> Set.of(peterDiamond.getUuid(), jordanHammond.getUuid()).contains(u.uuid()));
    }

    @Override
    protected void loadTestData() {
        testDataCreator.persistSuite(internalUserSuiteGeneric, auraEntityManager);
    }
}