package com.ubs.tools.cpt.shared.sql;

public record SelectExpression(SqlFragment fragment) implements Aliasable, Orderable {
    @Override
    public Selectable as(String alias) {
        return dialect -> dialect.as(this, alias);
    }

    @Override
    public String sql(SqlDialect dialect) {
        return "(" +  fragment.sql(dialect) + ")";
    }
}
