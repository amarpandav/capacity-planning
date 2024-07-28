package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class GenericSqlDialect implements SqlDialect {
    @Override
    public String fieldReference(String tableName, String field) {
        return tableName + "." + field;
    }

    @Override
    public String likeClause(String leftPart, String rightPart) {
        return leftPart + " LIKE " + rightPart;
    }

    @Override
    public String fromClause(FromClause fromClause) {
        return fromClause.tableName() + " " + fromClause.alias();
    }

    @Override
    public String joinClause(JoinClause joinClause) {
        return joinKeyword(joinClause.joinType()) + " " + joinClause.tableName() + " " + joinClause.alias() + " " + onClauseSql(joinClause.onClause());
    }

    @Override
    public String callFunction(String functionName, Collection<SqlFragment> args) {
        return functionName + "(" + args.stream().map(a -> a.sql(this)).collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public String as(SqlFragment fragment, String alias) {
        return fragment.sql(this) + " AS " + alias;
    }

    @Override
    public String whereBlock(ConditionalClause conditionalClause) {
        if (conditionalClause == null) {
            return "";
        }

        return "WHERE " + conditionalClause.sql(this) + "\n";
    }

    @Override
    public String orderByBlock(Collection<Orderable> orderables) {
        if (orderables.isEmpty()) {
            return "";
        }

        return "ORDER BY " + orderables.stream().filter(Objects::nonNull).map(x -> x.sql(this)).collect(Collectors.joining(", "));
    }

    @Override
    public String fromBlock(Collection<AliasClause> aliasClauses) {
        return "FROM " + joinAliases(aliasClauses) + " \n";
    }

    @Override
    public String selectBlock(Collection<Selectable> selectables) {
        return "SELECT " + joinFields(selectables) + " \n";
    }

    private static String joinKeyword(JoinClause.JoinType joinType) {
        return switch (joinType) {
            case LEFT -> "LEFT JOIN";
            case INNER -> "JOIN";
        };
    }

    private String onClauseSql(ConditionalClause conditionalClause) {
        return "ON (" + conditionalClause.sql(this) + ") ";
    }

    private String joinFields(Collection<Selectable> fragments) {
        return fragments.stream().map(fieldSelector -> fieldSelector.sql(this)).collect(Collectors.joining(", "));
    }

    private String joinAliases(Collection<AliasClause> aliases) {
        String froms = aliases.stream().filter(a -> a instanceof FromClause).map(c -> (FromClause) c).map(this::fromClause).collect(Collectors.joining(", "));
        String joins = aliases.stream().filter(a -> a instanceof JoinClause).map(c -> (JoinClause) c).map(this::joinClause).collect(Collectors.joining(", "));

        return froms + "\n" + joins + " ";
    }
}
