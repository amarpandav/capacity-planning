package com.amarpandav.cpt.service.dto;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.query.TransformationSource;
import com.amarpandav.cpt.infra.util.CollectionUtils;

/**
 * DTO to carry user's basic information.
 *
 * @author Amar Pandav
 */
public class UserDto {
    private EntityId<User> entityId;
    private String name;
    private UserKey userKey;
    private String jobTitle;
    private String country;
    private String orgUnitCode;
    private String orgUnitName;

    public static final class UserDtoTransformer implements CollectionUtils.Transformer<TransformationSource, UserDto> {
        @Override
        public UserDto transform(TransformationSource input) {
            EntityId<User> entityId = input.next().asEntityId();
            String name = input.next().asString();
            UserKey userKey = input.next().asUserKey();
            String jobTitle = input.next().asString();
            String country = input.next().asString();
            String orgUnitCode = input.next().asString();
            String orgUnitName = input.next().asString();
            return new UserDto(entityId, name, userKey, jobTitle, country, orgUnitCode, orgUnitName);
        }
    }

    public UserDto() {
        // Required by HttpMessageConverter to convert JSON to java object and
        // vice a versa
    }

    public UserDto(EntityId<User> entityId,
                    String name,
                    UserKey userKey,
                   String jobTitle,
                   String country,
                   String orgUnitCode,
                   String orgUnitName) {
        this.entityId = entityId;
        this.name = name;
        this.userKey = userKey;
        this.jobTitle = jobTitle;
        this.country = country;
        this.orgUnitCode = orgUnitCode;
        this.orgUnitName = orgUnitName;
    }

    public EntityId<User> getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public UserKey getUserKey() {
        return userKey;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public String getCountry() {
        return country;
    }

    public String getOrgUnitCode() {
        return orgUnitCode;
    }

    public String getOrgUnitName() {
        return orgUnitName;
    }
}
