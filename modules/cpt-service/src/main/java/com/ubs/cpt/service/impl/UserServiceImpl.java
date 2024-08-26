package com.ubs.cpt.service.impl;

import com.ubs.cpt.infra.datetime.DateTimeService;
import com.ubs.cpt.infra.spring.util.CptReadOnlyTransaction;
import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.query.UserQuery;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Amar Pandav
 */
@SuppressWarnings("JpaQlInspection")
@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
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
