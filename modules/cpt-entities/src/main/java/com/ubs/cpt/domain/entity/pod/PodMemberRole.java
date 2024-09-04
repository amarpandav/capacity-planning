package com.ubs.cpt.domain.entity.pod;

public enum PodMemberRole {
    JAVA_DEVELOPER("JD"),
    NET_DEVELOPER("ND"),
    LEAD_DEVELOPER("LD"),
    TESTER("TE"),
    POD_LEAD("PL"),
    SOLUTIONS_ARCHITECT("SA"),
    BUSINESS_ANALYSTS("BA"),
    PRODUCT_OWNER("PO"),
    UI_UX("UX"),
    POD_WATCHER("PW");

    PodMemberRole(String shortRoleName) {
    }

    public boolean isJavaDeveloper() {
        return this == PodMemberRole.JAVA_DEVELOPER;
    }

    public boolean isNetDeveloper() {
        return this == PodMemberRole.NET_DEVELOPER;
    }

    public boolean isLeadDeveloper() {
        return this == PodMemberRole.LEAD_DEVELOPER;
    }

    public boolean isTester() {
        return this == PodMemberRole.TESTER;
    }

    public boolean isPodLead() {
        return this == PodMemberRole.POD_LEAD;
    }

    public boolean isSolutionsArchitect() {
        return this == PodMemberRole.SOLUTIONS_ARCHITECT;
    }

    public boolean isBusinessAnalysts() {
        return this == PodMemberRole.BUSINESS_ANALYSTS;
    }

    public boolean isProductOwner() {
        return this == PodMemberRole.PRODUCT_OWNER;
    }

    public boolean isUiUx() {
        return this == PodMemberRole.UI_UX;
    }
}

