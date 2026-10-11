package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.infra.spring.util.CptReadOnlyTransaction;
import com.amarpandav.cpt.service.UserService;
import com.amarpandav.cpt.service.dto.UserDto;
import com.amarpandav.cpt.service.repository.UsersRepositoryImpl;
import com.amarpandav.cpt.service.searchparams.UserSearchParameters;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Amar Pandav
 */
@SuppressWarnings("JpaQlInspection")
@Service
public class UserServiceImpl implements UserService {
    private final UsersRepositoryImpl usersRepository;

    public UserServiceImpl(UsersRepositoryImpl usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @CptReadOnlyTransaction
    public List<UserDto> findUsers(UserSearchParameters searchParameters) {
        return usersRepository.findUsers(searchParameters);
    }

    @Override
    @CptReadOnlyTransaction
    public int countUsers(UserSearchParameters searchParameters) {
        return usersRepository.countUsers(searchParameters);
    }
}
