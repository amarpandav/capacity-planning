package com.ubs.tools.cpt.core.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode
@Builder
public class FindUsersQuery {
    private final UserSystem system;
    private final String fullNameLike;
    private final String emailLike;

    public FindUsersQuery(UserSystem system, String fullNameLike, String emailLike) {
        this.system = system;
        this.fullNameLike = fullNameLike;
        this.emailLike = emailLike;
    }

    public Optional<UserSystem> system() {
        return Optional.ofNullable(system);
    }

    public Optional<String> getFullNameLike() {
        return Optional.ofNullable(fullNameLike);
    }

    public Optional<String> getEmailLike() {
        return Optional.ofNullable(emailLike);
    }
}
