package com.ubs.tools.cpt.web.data.aura;

import com.ubs.tools.cpt.shared.sql.FromClause;
import com.ubs.tools.cpt.shared.sql.FieldSelector;

public class InternalUserTable {
    public static String TABLE_NAME = "INTERNAL_USER";

    private InternalUserTable(String alias) {
        this.tableFromClause = new FromClause(TABLE_NAME, alias);
        this.alias = alias;
    }

    public static InternalUserTable withAlias(String alias) {
        return new InternalUserTable(alias);
    }

    public static class Fields {
        public static final String UUID = "UUID";
        public static final String FULL_NAME = "FULL_NAME";
        public static final String EMAIL = "EMAIL";
    }

    private final FromClause tableFromClause;
    private final String alias;

    public String alias() {
        return alias;
    }

    public FromClause fromClause() {
        return tableFromClause;
    }

    public FieldSelector uuid() {
        return new FieldSelector(tableFromClause, Fields.UUID);
    }

    public FieldSelector fullName() {
        return new FieldSelector(tableFromClause, Fields.FULL_NAME);
    }

    public FieldSelector email() {
        return new FieldSelector(tableFromClause, Fields.EMAIL);
    }
}
