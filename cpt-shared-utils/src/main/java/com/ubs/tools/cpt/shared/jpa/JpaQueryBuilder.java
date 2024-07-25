package com.ubs.tools.cpt.shared.jpa;

import com.ubs.tools.cpt.shared.sql.SelectQuery;

import java.util.Collections;
import java.util.List;

public class JpaQueryBuilder {
    private final SelectQuery selectQuery;

    private JpaQueryBuilder(SelectQuery selectQuery) {
        this.selectQuery = selectQuery;
    }

    public static JpaQueryBuilder jpaQuery(SelectQuery selectQuery) {
        return new JpaQueryBuilder(selectQuery);
    }

    public JpaQuery jpaParams(JpaQuery.Param... params) {
        if (params == null || params.length == 0) {
            return new JpaQuery(selectQuery, Collections.emptySet());
        }

        return new JpaQuery(selectQuery, List.of(params));
    }

    public static JpaQuery.Param optionalParam(String name, Object value) {
        return new JpaQuery.Param(name, value, JpaQuery.Param.IncludeType.NOT_NULL);
    }  

    public static JpaQuery.Param requiredParam(String name, Object value) {
        return new JpaQuery.Param(name, value, JpaQuery.Param.IncludeType.ALWAYS);
    }
}
