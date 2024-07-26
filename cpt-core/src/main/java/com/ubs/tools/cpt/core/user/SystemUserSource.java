package com.ubs.tools.cpt.core.user;

import java.util.Optional;
import java.util.stream.Stream;

public interface SystemUserSource<ID extends SystemUserId> {
    UserSystem userSystem();

    Stream<SystemUser> findUsers(FindUsersQuery query);
    Optional<SystemUser> findUser(ID id);
}
