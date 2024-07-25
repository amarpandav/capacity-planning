package com.ubs.tools.cpt.shared.sql;

public record OrderableWrapper(Orderable orderable, SortOrder sortOrder, NullsOrder nullsOrder) implements Orderable {
    @Override
    public String sql(SqlDialect dialect) {
        return orderable.sql(dialect) + " " + sortOrder + " " + nullsOrder.getGenericSqlString();
    }
}
