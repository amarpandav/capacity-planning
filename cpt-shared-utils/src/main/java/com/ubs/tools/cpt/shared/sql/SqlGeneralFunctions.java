package com.ubs.tools.cpt.shared.sql;

import static com.ubs.tools.cpt.shared.sql.SqlQueryFunctions.func;

public class SqlGeneralFunctions {
    public static FunctionCall upper(SqlFragment fragment) {
        return func("upper", fragment);
    }

    public static FunctionCall lower(SqlFragment fragment) {
        return func("lower", fragment);
    }
}
