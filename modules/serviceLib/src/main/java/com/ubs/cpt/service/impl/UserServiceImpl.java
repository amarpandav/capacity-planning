package com.ubs.cpt.service.impl;

import com.google.common.base.Joiner;
import com.ubs.cpt.infra.spring.util.SmoothieReadonlyTransaction;
import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.query.UserQuery;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    private Joiner joiner = Joiner.on(",").skipNulls();

    @Override
    @SmoothieReadonlyTransaction
    public List<UserDto> findUsers(UserSearchParameters searchParameters) {
        UserQuery query = new UserQuery(em, false);
        query.with(searchParameters, true);

        return query.getResultList();
    }

    @Override
    @SmoothieReadonlyTransaction
    public int countUsers(UserSearchParameters searchParameters) {
        UserQuery query = new UserQuery(em, true);
        query.with(searchParameters, false);
        return query.countResults();
    }
}
