package com.ubs.cpt.shared.sql;

import com.ubs.cpt.shared.util.CollectionsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.ubs.cpt.shared.util.CollectionsUtil.listWithoutNulls;

public class SqlQueryFunctions {

    public static <T extends SqlFragment> IncludeBuilder<T> include(T fragment) {
        return new IncludeBuilder<>(fragment);
    }

    public static IncludeBuilder<RawFragment> include(String fragment) {
        return new IncludeBuilder<>(raw(fragment));
    }


    public static class IncludeBuilder<T extends SqlFragment> {
        private final T fragment;

        public IncludeBuilder(T fragment) {
            this.fragment = fragment;
        }

        public T whenNotNull(Object nullableValue) {
            if (nullableValue == null) {
                return null;
            }

            if (nullableValue instanceof Collection<?> c) {
                if (c.isEmpty()) {
                    return null;
                }
            }

            return fragment;
        }
    }

    public static SqlFragment nullValue() {
        return raw("null");
    }

    public static SqlFragment param(String name) {
        return raw(":" + name);
    }

    public static FromClause from(String table, String alias) {
        return new FromClause(table, alias);
    }

    public static JoinClauseBuilder innerJoin(String table, String alias) {
        return new JoinClauseBuilder(table, alias, JoinClause.JoinType.INNER);
    }

    public static class JoinClauseBuilder {
        private final String table;
        private final String alias;
        private final JoinClause.JoinType joinType;

        public JoinClauseBuilder(String table, String alias, JoinClause.JoinType joinType) {
            this.table = table;
            this.alias = alias;
            this.joinType = joinType;
        }

        public JoinClause on(Function<JoinClause, ConditionalClause> onFunc) {
            var initialClause = new JoinClause(table, alias, joinType, null);
            return new JoinClause(table, alias, joinType, onFunc.apply(initialClause));
        }

    }

    public static JoinClauseBuilder leftJoin(String table, String alias) {
        return new JoinClauseBuilder(table, alias, JoinClause.JoinType.LEFT);
    }

    public static RawFragment raw(String sql) {
        return new RawFragment(sql);
    }

    public static AndClause and(SqlFragment... sqlFragments) {
        return new AndClause(listWithoutNulls(sqlFragments));
    }

    public static OrClause or(SqlFragment... sqlFragments) {
        return new OrClause(listWithoutNulls(sqlFragments));
    }

    public static NotClause not(SqlFragment fragment) {
        return new NotClause(fragment);
    }

    public static SelectBuilder fromTables(AliasClause... clauses) {
        if (CollectionsUtil.arrayEmpty(clauses)) {
            throw new IllegalArgumentException("You must have at least one selected alias");
        }

        return new SelectBuilder(clauses);
    }

    public static FunctionCall func(String functionName, SqlFragment... args) {
        return new FunctionCall(functionName, listWithoutNulls(args));
    }

    public static SelectExpression expr(SqlFragment fragment) {
        return new SelectExpression(fragment);
    }

    public static SelectExpression subquery(SelectQuery query) {
        return new SelectExpression(query);
    }

    public static class SelectBuilder {
        private final List<AliasClause> aliasClauses;

        public SelectBuilder(AliasClause[] clauses) {
            this.aliasClauses = listWithoutNulls(clauses);
        }

        public WhereBuilder where(ConditionalClause conditionalClause) {
            return new WhereBuilder(conditionalClause);
        }

        public WhereBuilder where(ConditionalClause... clauses) {
            return new WhereBuilder(and(clauses));
        }

        public WhereBuilder noConditions() {
            return new WhereBuilder(null);
        }

        public class WhereBuilder {
            private final ConditionalClause conditionalClause;

            public WhereBuilder(ConditionalClause conditionalClause) {
                this.conditionalClause = conditionalClause;
            }

            public SelectQuery selectFields(Selectable... selectables) {
                return new GeneratedSelectQuery(listWithoutNulls(selectables), aliasClauses, conditionalClause, Collections.emptySet());
            }

            public OrderByBuilder orderBy(Orderable... orderables) {
                return new OrderByBuilder(listWithoutNulls(orderables));
            }

            public class OrderByBuilder {
                private final List<Orderable> orderByClauses;

                public OrderByBuilder(List<Orderable> orderByClauses) {
                    this.orderByClauses = orderByClauses;
                }

                public SelectQuery selectFields(Selectable... selectables) {
                    return new GeneratedSelectQuery(listWithoutNulls(selectables), aliasClauses, conditionalClause, orderByClauses);
                }
            }

        }
    }
}
