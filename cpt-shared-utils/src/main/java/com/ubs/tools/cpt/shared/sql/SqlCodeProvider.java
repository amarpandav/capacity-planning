package com.ubs.tools.cpt.shared.sql;

@FunctionalInterface
public interface SqlCodeProvider {
    String sql(SqlDialect dialect);
}
