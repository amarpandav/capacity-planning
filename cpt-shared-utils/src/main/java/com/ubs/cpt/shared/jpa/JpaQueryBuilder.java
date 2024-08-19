package com.ubs.cpt.shared.jpa;

import com.ubs.cpt.shared.sql.SelectQuery;
import com.ubs.cpt.shared.sql.SqlDialect;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class JpaQueryBuilder {
    private final Function<SqlDialect, String> selectQuerySupplier;

    private JpaQueryBuilder(Function<SqlDialect, String> selectQuerySupplier) {
        this.selectQuerySupplier = selectQuerySupplier;
    }

    public static JpaQueryBuilder jpaQuery(SelectQuery selectQuery) {
        return new JpaQueryBuilder(selectQuery::sql);
    }

    public static JpaQueryBuilder jpaQuery(String sqlQuery) {
        return new JpaQueryBuilder(dialect -> sqlQuery);
    }

    public JpaQuery jpaParams(JpaQuery.Param... params) {
        if (params == null || params.length == 0) {
            return new JpaQuery(selectQuerySupplier, Collections.emptySet());
        }

        return new JpaQuery(selectQuerySupplier, List.of(params));
    }

    public static JpaQuery.Param optionalParam(String name, Object value) {
        return new JpaQuery.Param(name, value, JpaQuery.Param.IncludeType.NOT_NULL);
    }  

    public static JpaQuery.Param requiredParam(String name, Object value) {
        return new JpaQuery.Param(name, value, JpaQuery.Param.IncludeType.ALWAYS);
    }
}
