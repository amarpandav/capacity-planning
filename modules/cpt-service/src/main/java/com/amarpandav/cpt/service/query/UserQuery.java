package com.amarpandav.cpt.service.query;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.datetime.DateTimeService;
import com.amarpandav.cpt.infra.query.NativeJpaQueryBuilder;
import com.amarpandav.cpt.infra.query.TransformationSource;
import com.amarpandav.cpt.infra.search.SortBy;
import com.amarpandav.cpt.infra.util.CollectionUtils;
import com.amarpandav.cpt.service.dto.UserDto;
import com.amarpandav.cpt.service.searchparams.UserSearchParameters;
import jakarta.persistence.EntityManager;

/**
 * Query to load users.
 *
 * @author Amar pandav
 */
public class UserQuery extends NativeJpaQueryBuilder<UserDto> {

    public UserQuery(EntityManager em, boolean count, DateTimeService dateTimeService) {
        super(em, dateTimeService, !count ?
                "select u.uuid, u.name, u.employee_id, u.job_title, u.country, ou.org_unit_code, ou.org_unit_name from cpt_user u "
                        + "left join cpt_org_unit ou on ou.uuid = u.org_unit_uuid "
                :
                "select count(u.uuid) from cpt_user u "
                        + "left join cpt_org_unit ou on ou.uuid = u.org_unit_uuid "
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
        and("lower(u.employee_id) = lower(:employeeId)", "employeeId", key.getEmployeeId());
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
    /**
     * Matches the given org unit and every unit below it, e.g. TEC-PLT matches TEC-PLT and TEC-PLT-01.
     */
    public UserQuery withOrgUnitCode(String orgUnitCode) {
        and("(lower(ou.org_unit_code) = lower(:ouCode) or lower(ou.org_unit_code) like lower(:ouCode) || '-%')",
                "ouCode", orgUnitCode);
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

        //parameters.getUserKey().ifPresent(employeeId -> withUserKey(new UserKey(employeeId)));
        parameters.getUserKey().ifPresent(this::withUserKey);

        parameters.getJobTitle().ifPresent(this::withJobTitle);

        parameters.getCountry().ifPresent(this::withCountry);

        parameters.getOrgUnitCode().ifPresent(this::withOrgUnitCode);

        if (sort) {
            for (SortBy sortBy : parameters.getSortBy()) {
                boolean ascending = sortBy.getDirection() == SortBy.SortDirection.ASCENDING;
                orderBy("u.name", ascending, ifTrue("name".equals(sortBy.getFieldName())));
                orderBy("u.job_title", ascending, ifTrue("jobTitle".equals(sortBy.getFieldName())));
                orderBy("u.country", ascending, ifTrue("country".equals(sortBy.getFieldName())));
                orderBy("ou.org_unit_code", ascending, ifTrue("orgUnitCode".equals(sortBy.getFieldName())));

            }
        }
        return this;
    }

    @Override
    public CollectionUtils.Transformer<TransformationSource, UserDto> getTransformer() {
        return input -> new UserDto.UserDtoTransformer().transform(input);
    }
}
