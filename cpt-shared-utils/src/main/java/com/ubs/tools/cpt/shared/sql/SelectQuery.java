package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;

public record SelectQuery(
    Collection<Selectable> selectables,
    Collection<AliasClause> aliasClauses,
    ConditionalClause conditionalClause,
    Collection<Orderable> orderByClauses
) implements SqlFragment {
    @Override
    public String sql(SqlDialect dialect) {
        return (
            dialect.selectBlock(selectables) +
                dialect.fromBlock(aliasClauses) +
                dialect.whereBlock(conditionalClause) +
                dialect.orderByBlock(orderByClauses)
        ).replaceAll("\\s*\\n+", "\n").trim();
    }
}
