package com.ubs.cpt.shared.sql;

public record FromClause(String tableName, String alias) implements AliasClause {
}
