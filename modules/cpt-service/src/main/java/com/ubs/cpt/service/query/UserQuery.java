package com.ubs.cpt.service.query;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserKey;
import com.ubs.cpt.infra.datetime.DateTimeService;
import com.ubs.cpt.infra.query.NativeJpaQueryBuilder;
import com.ubs.cpt.infra.query.TransformationSource;
import com.ubs.cpt.infra.search.SortBy;
import com.ubs.cpt.infra.util.CollectionUtils;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import jakarta.persistence.EntityManager;

/**
 * Query to load users.
 *
 * @author Amar pandav
 */
public class UserQuery extends NativeJpaQueryBuilder<UserDto> {

    public UserQuery(EntityManager em, boolean count, DateTimeService dateTimeService) {
        super(em, dateTimeService, !count ?
                "select u.uuid, u.name, u.gpin, u.job_title, u.country from cpt_user u "
                :
                "select count(u.uuid) from cpt_user u "
        );
    }

    public UserQuery withUserEntityId(EntityId<User> uEntityId) {
        and("u.uuid = :uUuid", "uUuid", uEntityId.getUuid());
        return this;
    }

    public UserQuery withUserName(String name) {
        andLike("lower(u.name) like lower(:uName)", "uName", name);
        return this;
    }

    public UserQuery withUserKey(UserKey key) {
        and("lower(u.gpin) = lower(:gpin)", "gpin", key.getGpin());
        return this;
    }

    public UserQuery withJobTitle(String jt) {
        andLike("lower(u.job_title) like lower(:jt)", "jt", jt);
        return this;
    }

    public UserQuery withCountry(String country) {
        andLike("lower(u.country) like lower(:country)", "country", country);
        return this;
    }
    /*public UserQuery withFullTextSearch(String searchCriteria) {
        if (!StringUtils.isBlank(searchCriteria)) {
            newSearchCriteriaBuilder()
                    .consider("u.name").asString()
                    .applyTo(this, searchCriteria);
        }
        return this;
    }*/

    public UserQuery with(UserSearchParameters parameters, boolean sort) {
        if (parameters.getEntityId() != null) {
            withUserEntityId(parameters.getEntityId());
        }

        //Global search
        //withFullTextSearch(parameters.getName());
        parameters.getName().ifPresent(this::withUserName);

        parameters.getGpin().ifPresent(gpin -> withUserKey(new UserKey(gpin)));

        parameters.getJobTitle().ifPresent(this::withJobTitle);

        parameters.getCountry().ifPresent(this::withCountry);

        if (sort) {
            for (SortBy sortBy : parameters.getSortBy()) {
                boolean ascending = sortBy.getDirection() == SortBy.SortDirection.ASCENDING;
                orderBy("u.name", ascending, ifTrue("name".equals(sortBy.getFieldName())));
                orderBy("u.job_title", ascending, ifTrue("jobTitle".equals(sortBy.getFieldName())));
                orderBy("u.country", ascending, ifTrue("country".equals(sortBy.getFieldName())));

            }
        }
        return this;
    }

    @Override
    public CollectionUtils.Transformer<TransformationSource, UserDto> getTransformer() {
        return input -> new UserDto.UserDtoTransformer().transform(input);
    }
}
