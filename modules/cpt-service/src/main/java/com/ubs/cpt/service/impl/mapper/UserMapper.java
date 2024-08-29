package com.ubs.cpt.service.impl.mapper;

import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.dto.UserDto;

public class UserMapper {
    private UserMapper() {
    }

    public static UserDto map(User user) {
        return new UserDto(user.getEntityId(), user.getName(), user.getKey(), user.getJobTitle(), user.getCountry());
    }
}
