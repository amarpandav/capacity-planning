package com.amarpandav.cpt.service.repository;

import com.amarpandav.cpt.infra.datetime.DateTimeService;
import com.amarpandav.cpt.service.dto.UserDto;
import com.amarpandav.cpt.service.query.UserQuery;
import com.amarpandav.cpt.service.searchparams.UserSearchParameters;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepositoryImpl {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DateTimeService dateTimeService;

    public List<UserDto> findUsers(UserSearchParameters searchParameters) {
        UserQuery query = new UserQuery(em, false, dateTimeService);
        query.with(searchParameters, true);

        return query.getResultList();
    }

    public int countUsers(UserSearchParameters searchParameters) {
        UserQuery query = new UserQuery(em, true, dateTimeService);
        query.with(searchParameters, false);
        return query.countResults();
    }
}
