package com.ubs.tools.cpt.core.user.aura;

import com.ubs.tools.cpt.core.user.SystemUser;
import com.ubs.tools.cpt.core.user.UserSystem;

public record AuraUser(
    AuraUserId id,
    String fullName,
    String email,
    AuraSpecificAttributes systemSpecificAttributes
) implements SystemUser {
    @Override
    public UserSystem sourceSystem() {
        return UserSystem.AURA;
    }
}