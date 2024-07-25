package com.ubs.tools.cpt.core.user;

public record SystemUser(
    SystemUserId id,
    String fullName,
    String email
) {}