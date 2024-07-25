package com.ubs.tools.cpt.shared.sql;

public record OperatorClause(SqlFragment left, SqlFragment right, String operator) implements ConditionalClause {
    @Override
    public String sql(SqlDialect dialect) {
        return left.sql(dialect) + " " + operator + " " + right.sql(dialect);
    }
}
