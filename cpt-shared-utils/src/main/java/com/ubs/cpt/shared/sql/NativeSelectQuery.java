package com.ubs.cpt.shared.sql;

public record NativeSelectQuery(
    SqlCodeProvider selectFrom,
    SqlCodeProvider where,
    SqlCodeProvider orderBy
) implements SelectQuery {
    @Override
    public String sql(SqlDialect dialect) {
        return (selectFrom().sql(dialect) + "\n" + where.sql(dialect) + "\n" + orderBy.sql(dialect))
            .replaceAll("\\n+", "\n")
            .trim();
    }
}
