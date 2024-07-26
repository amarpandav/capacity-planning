package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;

public interface SqlDialect {
    GenericSqlDialect GENERIC = new GenericSqlDialect();

    String fieldReference(String tableName, String field);
    String likeClause(String leftPart, String rightPart);

    String fromClause(FromClause fromClause);

    String joinClause(JoinClause joinClause);

    String callFunction(String functionName, Collection<SqlFragment> args);

    String as(SqlFragment fragment, String alias);
}
