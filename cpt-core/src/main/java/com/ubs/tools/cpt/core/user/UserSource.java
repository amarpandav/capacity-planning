package com.ubs.tools.cpt.core.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class dispatches {@link SystemUser}-search queries among multiple {@link SystemUserSource}s.
 * This is the class you want to use to fetch users. Do not use individual {@link SystemUserSource} without a good reason.
 */
public class UserSource {
    private final Collection<SystemUserSource<?>> systemUserSources;

    public UserSource(Collection<SystemUserSource<?>> systemUserSources) {
        this.systemUserSources = Objects.requireNonNull(systemUserSources);
    }

    public Stream<SystemUser> findUsers(FindUsersQuery query) {
        Objects.requireNonNull(query);

        var sources = getSourcesForQuery(query);

        return sources.stream().flatMap(s -> s.findUsers(query));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Optional<SystemUser> findUser(SystemUserId systemUserId) {
        Objects.requireNonNull(systemUserId);

        SystemUserSource systemUserSource = findSourceBySystem(systemUserId.userSystem());

        return systemUserSource.findUser(systemUserId);
    }

    private Collection<SystemUserSource<?>> getSourcesForQuery(FindUsersQuery query) {
        Optional<UserSystem> system = query.system();

        if (system.isEmpty()) {
            return systemUserSources;
        }

        return Collections.singleton(
            findSourceBySystem(system.get())
        );
    }

    private SystemUserSource<?> findSourceBySystem(UserSystem system) {
        return systemUserSources
            .stream()
            .filter(s -> s.userSystem() == system)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No SystemUserSource found for " + system));
    }
}
