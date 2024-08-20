package com.ubs.cpt.service.query;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.datetime.DateTimeService;
import com.ubs.cpt.infra.query.NativeJpaQueryBuilder;
import com.ubs.cpt.infra.query.TransformationSource;
import com.ubs.cpt.infra.search.SortBy;
import com.ubs.cpt.infra.util.CollectionUtils;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;

import static com.ubs.cpt.infra.query.util.SearchCriteriaFilterBuilder.newSearchCriteriaBuilder;

/**
 * Query to load smoothieu.
 *
 * @author Amar pandav
 */
public class UserQuery extends NativeJpaQueryBuilder<UserDto> {

    public UserQuery(EntityManager em, boolean count, DateTimeService dateTimeService) {
        super(em, dateTimeService, !count ?
                        "select u.uuid, u.name, u.gpin from user u "
                        :
                        "select count(u.uuid) from user u "
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

    public UserQuery withFullTextSearch(String searchCriteria) {
        if (!StringUtils.isBlank(searchCriteria)) {
            newSearchCriteriaBuilder()
                    .consider("u.name").asString()
                    .applyTo(this, searchCriteria);
        }
        return this;
    }

    public UserQuery with(UserSearchParameters parameters, boolean sort) {
        if (parameters.getEntityId() != null) {
            withUserEntityId(parameters.getEntityId());
        }

        //Global search
        withFullTextSearch(parameters.getSearchCriteria());

        if (sort) {
            for (SortBy sortBy : parameters.getSortBy()) {
                boolean ascending = sortBy.getDirection() == SortBy.SortDirection.ASCENDING;
                orderBy("u.name", ascending, ifTrue("name".equals(sortBy.getFieldName())));
            }
        }
        return this;
    }

    @Override
    public CollectionUtils.Transformer<TransformationSource, UserDto> getTransformer() {
        return new CollectionUtils.Transformer<TransformationSource, UserDto>() {
            @Override
            public UserDto transform(TransformationSource input) {
                return new UserDto.UserDtoTransformer().transform(input);
            }
        };
    }
}
