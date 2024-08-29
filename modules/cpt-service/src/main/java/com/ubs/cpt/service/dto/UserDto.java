package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserKey;
import com.ubs.cpt.infra.query.TransformationSource;
import com.ubs.cpt.infra.util.CollectionUtils;

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

    public static final class UserDtoTransformer implements CollectionUtils.Transformer<TransformationSource, UserDto> {
        @Override
        public UserDto transform(TransformationSource input) {
            EntityId<User> entityId = input.next().asEntityId();
            String name = input.next().asString();
            UserKey userKey = input.next().asUserKey();
            String jobTitle = input.next().asString();
            String country = input.next().asString();
            return new UserDto(entityId, country, userKey, jobTitle, country);
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
                   String country) {
        this.entityId = entityId;
        this.name = name;
        this.userKey = userKey;
        this.jobTitle = jobTitle;
        this.country = country;
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
}
