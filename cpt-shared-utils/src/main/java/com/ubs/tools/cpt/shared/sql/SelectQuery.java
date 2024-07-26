package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;
import java.util.stream.Collectors;

public record SelectQuery(Collection<Selectable> selecatbles, Collection<AliasClause> aliasClauses,
                          ConditionalClause conditionalClause, Collection<Orderable> orderByClauses) implements SqlFragment {
    @Override
    public String sql(SqlDialect dialect) {
        return (
            "SELECT " + joinFields(selecatbles, dialect) + " \n"
            + "FROM " + joinAliases(aliasClauses, dialect) + " \n" +
            renderWhere(conditionalClause, dialect) +
            joinOrderBy(orderByClauses, dialect)
        ).replaceAll("\\s*\\n+", "\n").trim();
    }

    private static String renderWhere(ConditionalClause conditionalClause, SqlDialect dialect) {
        if (conditionalClause == null) {
            return "";
        }

        return "WHERE " + conditionalClause.sql(dialect) + "\n";
    }

    private static String joinFields(Collection<Selectable> fragments, SqlDialect dialect) {
        return fragments.stream().map(fieldSelector -> fieldSelector.sql(dialect)).collect(Collectors.joining(", "));
    }

    private static String joinAliases(Collection<AliasClause> aliases, SqlDialect dialect) {
        String froms = aliases.stream().filter(a -> a instanceof FromClause).map(c -> (FromClause) c).map(dialect::fromClause).collect(Collectors.joining(", "));
        String joins = aliases.stream().filter(a -> a instanceof JoinClause).map(c -> (JoinClause) c).map(dialect::joinClause).collect(Collectors.joining(", "));

        return froms + "\n" + joins + " ";
    }

    private static String joinOrderBy(Collection<Orderable> orderByClauses, SqlDialect dialect) {
        if (orderByClauses.isEmpty()) {
            return "";
        }

        return "ORDER BY " + orderByClauses.stream().map(x -> x.sql(dialect)).collect(Collectors.joining(", "));
    }


}
