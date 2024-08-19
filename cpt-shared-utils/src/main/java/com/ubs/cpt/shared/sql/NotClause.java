package com.ubs.cpt.shared.sql;

public record NotClause(SqlFragment fragment) implements ConditionalClause {
    @Override
    public String sql(SqlDialect dialect) {
        return " NOT (" + fragment.sql(dialect) + ")";
    }
}
