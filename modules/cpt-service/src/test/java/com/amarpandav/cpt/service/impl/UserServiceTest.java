package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.infra.test.base.TestBase;
import com.amarpandav.cpt.service.UserService;
import com.amarpandav.cpt.service.dto.UserDto;
import com.amarpandav.cpt.service.searchparams.UserSearchParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Service to get users.
 *
 * @author Amar Pandav
 */
public class UserServiceTest extends TestBase {

    @Autowired
    UserService userService;


    @Test
    public void testFindUsers(){
        List<UserDto> users = userService.findUsers(new UserSearchParameters());
        assertThat(users).isNotNull();
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void testFindUsersReturnsOrgUnit() {
        List<UserDto> users = userService.findUsers(new UserSearchParameters().withName("Amar Pandav"));
        assertThat(users).singleElement()
                .satisfies(u -> {
                    assertThat(u.getOrgUnitCode()).isEqualTo("TEC-PLT-01");
                    assertThat(u.getOrgUnitName()).isEqualTo("Identity & Access Services");
                });
    }

    @Test
    public void testFindUsersByOrgUnitIncludesUnitsBelow() {
        List<UserDto> users = userService.findUsers(new UserSearchParameters().withOrgUnitCode("TEC-PLT"));
        assertThat(users).extracting(UserDto::getName).containsExactlyInAnyOrder(
                "Amar Pandav", "Liam Parker", "Tobias Berg", "Oliver Grant", "Kevin Lang", "Hannah Reed");
    }

    @Test
    public void testFindUsersByTeamOrgUnit() {
        List<UserDto> users = userService.findUsers(new UserSearchParameters().withOrgUnitCode("TEC-PLT-02"));
        assertThat(users).extracting(UserDto::getName).containsExactlyInAnyOrder("Tobias Berg", "Kevin Lang");
    }

    @Test
    public void testFindUsersByPartialOrgUnitCodeMatchesNothing() {
        List<UserDto> users = userService.findUsers(new UserSearchParameters().withOrgUnitCode("TEC-P"));
        assertThat(users).isEmpty();
    }
}
