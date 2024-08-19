package com.ubs.cpt.core.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Optional;

@ToString
@EqualsAndHashCode
@Builder
public class FindUsersQuery {
    private final Collection<UserSystem> systems;
    private final String fullNameLike;
    private final String emailLike;

    public FindUsersQuery(Collection<UserSystem> systems, String fullNameLike, String emailLike) {
        this.systems = systems;
        this.fullNameLike = fullNameLike;
        this.emailLike = emailLike;
    }

    public Collection<UserSystem> systems() {
        return systems;
    }

    public Optional<String> getFullNameLike() {
        return Optional.ofNullable(fullNameLike);
    }

    public Optional<String> getEmailLike() {
        return Optional.ofNullable(emailLike);
    }
}
