package com.ubs.tools.cpt.shared.sql;

import static com.ubs.tools.cpt.shared.sql.SqlQueryFunctions.func;

public class OracleFunctions {
    public static FunctionCall toUpper(SqlFragment fragment) {
        return func("to_upper", fragment);
    }

    public static FunctionCall toLower(SqlFragment fragment) {
        return func("to_lower", fragment);
    }
}
