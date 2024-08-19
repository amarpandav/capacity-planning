package com.ubs.cpt.shared.sql;

public record FieldSelector(AliasClause aliasClause, String field) implements Aliasable, Orderable {
    @Override
    public String sql(SqlDialect dialect) {
        return dialect.fieldReference(aliasClause.alias(), field);
    }

    @Override
    public Selectable as(String alias) {
        return dialect -> dialect.as(this, alias);
    }
}
