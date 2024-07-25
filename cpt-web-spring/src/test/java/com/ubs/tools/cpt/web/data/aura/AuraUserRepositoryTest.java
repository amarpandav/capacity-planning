package com.ubs.tools.cpt.web.data.aura;

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

    void insertInternalUser(String uuid, String fullName, String email) {
        auraEntityManager
            .createNativeQuery("INSERT INTO INTERNAL_USER (UUID, FULL_NAME, EMAIL) VALUES(:uuid, :fullName, :email)")
            .setParameter("uuid", uuid)
            .setParameter("fullName", fullName)
            .setParameter("email", email)
            .executeUpdate();
    }

    @Test
    @AuraTransactional
    void findUsers_byUuid() {
        insertInternalUser("1234", "ABBA BABBA", "abba.babba@ubs.com");
        insertInternalUser("4321", "CEPPE DEPPE", "ceppe.deppe@ubs.com");

        auraUserRepository.findUsers("1234", null, null)
                          .findFirst()
                          .ifPresentOrElse(
                              user -> {
                                  assertEquals("ABBA BABBA", user.fullName());
                                  assertEquals("abba.babba@ubs.com", user.email());
                              },
                              () -> {
                                  throw new AssertionError("User not found");
                              }
                          );
        auraUserRepository.findUsers("4321", null, null)
                          .findFirst()
                          .ifPresentOrElse(
                              user -> {
                                  assertEquals("CEPPE DEPPE", user.fullName());
                                  assertEquals("ceppe.deppe@ubs.com", user.email());
                              },
                              () -> {
                                  throw new AssertionError("User not found");
                              }
                          );
    }

    @Test
    @AuraTransactional
    void findUsers_by_FullName() {
        insertInternalUser("1111", "ANAME GANAME", "ag@ubs.com");
        insertInternalUser("2222", "BNAME YUPPO", "gn.yup@ubs.com");
        insertInternalUser("3333", "CDEPPE HOOW", "cde.ho@ubs.com");

        var users = auraUserRepository.findUsers(null, "name", null).toList();

        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals("1111"), "First user found"));
        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals("2222"), "Second user found"));
        assertThatCollection(users).noneMatch(u -> u.uuid().equals("3333"));
    }

    @Test
    @AuraTransactional
    void findUsers_by_Email() {
        insertInternalUser("1111", "ANAME GANAME", "agde@ubs.com");
        insertInternalUser("2222", "BNAME YUPPO", "gn.yup@ubs.com");
        insertInternalUser("3333", "CDEPPE HOOW", "cde.ho@ubs.com");

        var users = auraUserRepository.findUsers(null, null, "de").toList();

        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals("1111"), "First user found"));
        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals("3333"), "Third user found"));
        assertThatCollection(users).noneMatch(u -> u.uuid().equals("2222"));
    }

    @Test
    @AuraTransactional
    void findUsers_by_Email_and_FullName() {
        insertInternalUser("1111", "ANAME GANAME", "agde@ubs.com");
        insertInternalUser("2222", "BNAME YUPPO", "gn.yup@ubs.com");
        insertInternalUser("3333", "CDEPPE HOOW", "cde.ho@ubs.com");
        insertInternalUser("4444", "YYYYY YUPPO", "gns.yup@ubs.com");
        insertInternalUser("5555", "BBBBBB HOOW", "cdeg.ho@ubs.com");
        insertInternalUser("6666", "UUUUU GANAME", "agtde@ubs.com");

        var users = auraUserRepository.findUsers(null, "name", "de").toList();

        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals("1111"), "First user found"));
        assertThatCollection(users).areExactly(1, new Condition<>(u -> u.uuid().equals("6666"), "Second user found"));
        assertThatCollection(users).noneMatch(u -> Set.of("2222", "3333", "4444", "5555").contains(u.uuid()));
    }
}