package com.ubs.cpt.shared.sql;

@FunctionalInterface
public interface SqlCodeProvider {
    String sql(SqlDialect dialect);
}
