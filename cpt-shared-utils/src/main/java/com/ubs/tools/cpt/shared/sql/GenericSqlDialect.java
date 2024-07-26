package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;
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

    private static String joinKeyword(JoinClause.JoinType joinType) {
        return switch (joinType) {
            case LEFT -> "LEFT JOIN";
            case INNER -> "JOIN";
        };
    }

    private String onClauseSql(ConditionalClause conditionalClause) {
        return "ON (" + conditionalClause.sql(this) + ") ";
    }
}
