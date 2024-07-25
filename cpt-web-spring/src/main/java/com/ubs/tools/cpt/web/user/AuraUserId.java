package com.ubs.tools.cpt.web.user;

import com.ubs.tools.cpt.core.user.SystemUserId;
import com.ubs.tools.cpt.core.user.UserSystem;

/**
 * This class denotes an ID of user coming from CS's AURA system.
 *
 * @param uuid {@code INTERNAL_USER.UUID}. Must not be {@code null}
 */
public record AuraUserId(String uuid) implements SystemUserId {
    @Override
    public UserSystem userSystem() {
        return UserSystem.AURA;
    }
}
