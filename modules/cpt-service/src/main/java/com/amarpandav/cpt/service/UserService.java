package com.amarpandav.cpt.service;

import com.amarpandav.cpt.service.dto.UserDto;
import com.amarpandav.cpt.service.searchparams.UserSearchParameters;

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
