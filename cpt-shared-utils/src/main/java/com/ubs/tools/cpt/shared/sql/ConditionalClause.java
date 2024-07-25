package com.ubs.tools.cpt.shared.sql;

public sealed interface ConditionalClause extends SqlFragment permits AndClause, OrClause, NotClause, OperatorClause {
}
