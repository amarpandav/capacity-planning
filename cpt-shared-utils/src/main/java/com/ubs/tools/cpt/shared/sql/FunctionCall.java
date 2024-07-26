package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;

public record FunctionCall(String functionName, Collection<SqlFragment> arguments) implements SqlFragment {
    @Override
    public String sql(SqlDialect dialect) {
        return dialect.callFunction(functionName, arguments);
    }
}
