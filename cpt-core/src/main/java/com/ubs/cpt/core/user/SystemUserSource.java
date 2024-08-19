package com.ubs.cpt.core.user;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents a user source for a particular system.
 *
 * @param <ID> A class/record implementing {@link SystemUserId} for a specific system
 */
public interface SystemUserSource<ID extends SystemUserId> {
    UserSystem userSystem();

    Stream<SystemUser> findUsers(FindUsersQuery query);
    Optional<SystemUser> findUser(ID id);
}
