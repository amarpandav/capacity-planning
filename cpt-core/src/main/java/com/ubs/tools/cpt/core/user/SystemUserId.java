package com.ubs.tools.cpt.core.user;

/**
 * The in-database ID of a user. Each system might have its own ID type (it might be UUID, but it might be LONG too),
 * so the {@link SystemUserId} interface itself does not contain any field other than userSystem,
 * which will tell us which system we're dealing with.
 */
public interface SystemUserId {
    UserSystem userSystem();
}
