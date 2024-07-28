package com.ubs.tools.cpt.shared.sql;

import jakarta.persistence.EntityManager;

import java.util.Collection;

public interface SqlDialect {
    GenericSqlDialect GENERIC = new GenericSqlDialect();

    static SqlDialect fromEntityManager(EntityManager em) {
        // For now there is only GENERIC dialect. In the future we may use em.getEntityManagerFactory().getProperties()
        // to determine the SQL driver thus finding the exact dialect.
        return GENERIC;
    }

    String fieldReference(String tableName, String field);
    String likeClause(String leftPart, String rightPart);

    String fromClause(FromClause fromClause);

    String joinClause(JoinClause joinClause);

    String callFunction(String functionName, Collection<SqlFragment> args);

    String as(SqlFragment fragment, String alias);

    String whereBlock(ConditionalClause conditionalClause);

    String orderByBlock(Collection<Orderable> orderables);

    String fromBlock(Collection<AliasClause> aliasClauses);

    String selectBlock(Collection<Selectable> selectables);
}
