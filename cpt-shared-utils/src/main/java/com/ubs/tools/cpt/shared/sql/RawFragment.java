package com.ubs.tools.cpt.shared.sql;

public record RawFragment(String rawSql) implements SqlFragment, ConditionalClause {
    @Override
    public String sql(SqlDialect dialect) {
        return rawSql;
    }
}
