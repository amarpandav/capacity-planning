package com.ubs.cpt.service.impl;

import com.ubs.cpt.infra.spring.util.CptReadOnlyTransaction;
import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
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
