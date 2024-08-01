package com.ubs.tools.cpt.shared.sql;

import static com.ubs.tools.cpt.shared.util.CollectionsUtil.listWithoutNulls;

public class NativeSqlQueryFunctions {
    public static class NativeSqlQueryBuilder {
        private final String selectFromSql;

        private NativeSqlQueryBuilder(String selectFromSql) {
            this.selectFromSql = selectFromSql;
        }

        public WhereBuilder where() {
            return new WhereBuilder(x -> "");
        }

        public WhereBuilder where(ConditionalClause... conditionalClauses) {
            return new WhereBuilder(dialect -> dialect.whereBlock(SqlQueryFunctions.and(conditionalClauses)));
        }

        public WhereBuilder.OrderByBuilder orderBy(Orderable... orderables) {
            return where().orderBy(orderables);
        }

        public WhereBuilder.OrderByBuilder build() {
            return orderBy();
        }

        public WhereBuilder where(String whereSql) {
            return new WhereBuilder(x -> "WHERE + " + whereSql);
        }

        public WhereBuilder where(ConditionalClause condition) {
            return new WhereBuilder(dialect -> dialect.whereBlock(condition));
        }

        public class WhereBuilder {
            private final SqlCodeProvider whereSupplier;

            public WhereBuilder(SqlCodeProvider whereSupplier) {
                this.whereSupplier = whereSupplier;
            }

            public OrderByBuilder orderBy(String orderBy) {
                return new OrderByBuilder(x -> orderBy);
            }

            public OrderByBuilder orderBy(Orderable... orderables) {
                return new OrderByBuilder(dialect -> dialect.orderByBlock(listWithoutNulls(orderables)));
            }

            public NativeSelectQuery build() {
                return orderBy().build();
            }

            public class OrderByBuilder {
                private final SqlCodeProvider orderBySupplier;

                public OrderByBuilder(SqlCodeProvider orderBySupplier) {
                    this.orderBySupplier = orderBySupplier;
                }
                
                public NativeSelectQuery build() {
                    return new NativeSelectQuery(
                        d -> selectFromSql,
                        whereSupplier,
                        orderBySupplier
                    );
                }
            }
        }
    }

    public static NativeSqlQueryBuilder nativeSqlQuery(String selectFrom) {
        return new NativeSqlQueryBuilder(selectFrom);
    }
}
