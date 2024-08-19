package com.ubs.cpt.shared.sql;

import java.util.Collection;

public record GeneratedSelectQuery(
    Collection<Selectable> selectables,
    Collection<AliasClause> aliasClauses,
    ConditionalClause conditionalClause,
    Collection<Orderable> orderByClauses
) implements SelectQuery {
    @Override
    public String sql(SqlDialect dialect) {
        return (""
            + dialect.selectBlock(selectables)
            + dialect.fromBlock(aliasClauses)
            + dialect.whereBlock(conditionalClause)
            + dialect.orderByBlock(orderByClauses)
        ).replaceAll("\\n+", "\n")
         .replaceAll("\\s+\\n", "\n")
         .trim();
    }
}
