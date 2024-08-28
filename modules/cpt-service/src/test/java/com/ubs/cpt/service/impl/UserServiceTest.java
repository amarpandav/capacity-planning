package com.ubs.cpt.service.impl;

import com.ubs.cpt.infra.test.base.TestBase;
import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
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
}
