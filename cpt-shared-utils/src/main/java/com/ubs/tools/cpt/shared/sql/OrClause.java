package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public record OrClause(Collection<SqlFragment> fragments) implements ConditionalClause {
    @Override
    public String sql(SqlDialect dialect) {
        if (fragments.isEmpty()) {
            return "1=1";
        }

        return "(" + fragments.stream().filter(Objects::nonNull).map(c -> "(" + c.sql(dialect) + ")").collect(Collectors.joining(" OR ")) + ")";
    }
}
