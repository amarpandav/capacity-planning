package com.amarpandav.cpt.service.impl.mapper;

import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.service.dto.UserDto;

public class UserMapper {
    private UserMapper() {
    }

    public static UserDto map(User user) {
        return new UserDto(user.getEntityId(), user.getName(), user.getKey(), user.getJobTitle(), user.getCountry());
    }
}
