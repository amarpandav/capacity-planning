package com.ubs.tools.cpt.shared.sql;

public sealed interface AliasClause extends Orderable permits FromClause, JoinClause {
    String alias();
    String tableName();

    default String sql(SqlDialect dialect) {
        return alias();
    }

    default FieldSelector field(String fieldName) {
        return new FieldSelector(this, fieldName);
    }

    default FieldSelector allFields() {
        return field("*");
    }
}
