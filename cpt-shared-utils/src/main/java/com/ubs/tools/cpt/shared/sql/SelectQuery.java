package com.ubs.tools.cpt.shared.sql;

public interface SelectQuery extends SqlFragment {
    @Override
    String sql(SqlDialect dialect);
}
