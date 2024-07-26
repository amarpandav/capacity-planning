package com.ubs.tools.cpt.shared.sql;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class SqlQueryFunctions {

    private static <T> List<T> toList(T[] args) {
        if (arrayEmpty(args)) {
            return Collections.emptyList();
        }

        return Stream.of(args).filter(Objects::nonNull).toList();
    }

    public static <T extends SqlFragment> IncludeBuilder<T> include(T fragment) {
        return new IncludeBuilder<>(fragment);
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

    private static <T> boolean arrayEmpty(T[] args) {
        return args == null || args.length == 0;
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

    public static SqlFragment raw(String sql) {
        return new RawFragment(sql);
    }

    public static AndClause and(SqlFragment... sqlFragments) {
        return new AndClause(toList(sqlFragments));
    }

    public static OrClause or(SqlFragment... sqlFragments) {
        return new OrClause(toList(sqlFragments));
    }

    public static NotClause not(SqlFragment fragment) {
        return new NotClause(fragment);
    }

    public static SelectBuilder fromTables(AliasClause... clauses) {
        if (arrayEmpty(clauses)) {
            throw new IllegalArgumentException("You must have at least one selected alias");
        }

        return new SelectBuilder(clauses);
    }

    public static FunctionCall func(String functionName, SqlFragment... args) {
        return new FunctionCall(functionName, toList(args));
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
            this.aliasClauses = toList(clauses);
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
                return new SelectQuery(toList(selectables), aliasClauses, conditionalClause, Collections.emptySet());
            }

            public OrderByBuilder orderBy(Orderable... orderables) {
                return new OrderByBuilder(toList(orderables));
            }

            public class OrderByBuilder {
                private final List<Orderable> orderByClauses;

                public OrderByBuilder(List<Orderable> orderByClauses) {
                    this.orderByClauses = orderByClauses;
                }

                public SelectQuery selectFields(Selectable... selectables) {
                    return new SelectQuery(toList(selectables), aliasClauses, conditionalClause, orderByClauses);
                }
            }

        }
    }
}
