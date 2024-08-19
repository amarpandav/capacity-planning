package com.ubs.cpt.shared.sql;

@FunctionalInterface
public interface SqlFragment extends SqlCodeProvider {
    default OperatorClause eq(SqlFragment other) {
        return new OperatorClause(this, other, "=");
    }

    default OperatorClause neq(SqlFragment other) {
        return new OperatorClause(this, other, "<>");
    }

    default OperatorClause lt(SqlFragment other) {
        return new OperatorClause(this, other, "<");
    }

    default OperatorClause le(SqlFragment other) {
        return new OperatorClause(this, other, "<=");
    }

    default OperatorClause gt(SqlFragment other) {
        return new OperatorClause(this, other, ">");
    }

    default OperatorClause ge(SqlFragment other) {
        return new OperatorClause(this, other, ">=");
    }

    default OperatorClause like(SqlFragment other) {
        return new OperatorClause(this, other, "LIKE");
    }

    default OperatorClause in(SqlFragment other) {
        return new OperatorClause(this, other, "IN");
    }

    default OperatorClause notIn(SqlFragment other) {
        return new OperatorClause(this, other, "NOT IN");
    }
}
