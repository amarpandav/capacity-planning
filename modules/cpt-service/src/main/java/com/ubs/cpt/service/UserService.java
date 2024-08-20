package com.ubs.cpt.service;

import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;

import java.util.List;

/**
 * Service to get users.
 *
 * @author Amar Pandav
 */
public interface UserService {

    List<UserDto> findUsers(UserSearchParameters searchParameters);

    int countUsers(UserSearchParameters searchParameters);
}
