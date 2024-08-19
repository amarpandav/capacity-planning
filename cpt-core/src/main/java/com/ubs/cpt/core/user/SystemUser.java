package com.ubs.cpt.core.user;

/**
 * This interface represents a user imported from a given system.
 */
public interface SystemUser {
    UserSystem sourceSystem();

    SystemUserId id();
    String fullName();
    String email();
    SystemSpecificAttributes systemSpecificAttributes();
}