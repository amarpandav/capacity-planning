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

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DateTimeService dateTimeService;

    @Override
    @CptReadOnlyTransaction
    public List<UserDto> findUsers(UserSearchParameters searchParameters) {
        UserQuery query = new UserQuery(em, false, dateTimeService);
        query.with(searchParameters, true);

        return query.getResultList();
    }

    @Override
    @CptReadOnlyTransaction
    public int countUsers(UserSearchParameters searchParameters) {
        UserQuery query = new UserQuery(em, true, dateTimeService);
        query.with(searchParameters, false);
        return query.countResults();
    }
}
