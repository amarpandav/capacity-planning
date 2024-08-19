package com.ubs.cpt.shared.sql;

public enum NullsOrder {
    NULLS_FIRST("NULLS FIRST"),
    NULLS_LAST("NULLS LAST");

    private final String genericSqlString;

    NullsOrder(final String genericSqlString) {

        this.genericSqlString = genericSqlString;
    }

    public String getGenericSqlString() {
        return genericSqlString;
    }
}
