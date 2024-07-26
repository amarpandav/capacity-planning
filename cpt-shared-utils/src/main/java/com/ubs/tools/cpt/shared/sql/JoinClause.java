package com.ubs.tools.cpt.shared.sql;

public record JoinClause(String tableName, String alias, JoinType joinType, ConditionalClause onClause) implements AliasClause {
    enum JoinType {
        INNER,
        LEFT
    }
}
