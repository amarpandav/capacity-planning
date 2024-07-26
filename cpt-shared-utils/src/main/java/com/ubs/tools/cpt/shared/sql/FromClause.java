package com.ubs.tools.cpt.shared.sql;

public record FromClause(String tableName, String alias) implements AliasClause {
}
