package com.ubs.tools.cpt.shared.sql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.function.Function;

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
            private final Function<SqlDialect, String> whereSupplier;

            public WhereBuilder(Function<SqlDialect, String> whereSupplier) {
                this.whereSupplier = whereSupplier;
            }

            public OrderByBuilder orderBy(String orderBy) {
                return new OrderByBuilder(x -> orderBy);
            }

            public OrderByBuilder orderBy(Orderable... orderables) {
                return new OrderByBuilder(dialect -> dialect.orderByBlock(listWithoutNulls(orderables)));
            }

            public OrderByBuilder build() {
                return orderBy();
            }

            public class OrderByBuilder {
                private final Function<SqlDialect, String> orderBySupplier;

                public OrderByBuilder(Function<SqlDialect, String> orderBySupplier) {
                    this.orderBySupplier = orderBySupplier;
                }

                public String sql(SqlDialect dialect) {
                    return selectFromSql + '\n' + whereSupplier.apply(dialect) + orderBySupplier.apply(dialect);
                }

                public String sql(EntityManager em) {
                    return sql(SqlDialect.fromEntityManager(em));
                }

                @SuppressWarnings("SqlSourceToSinkFlow")
                public Query build(EntityManager em) {
                    return em.createNativeQuery(sql(SqlDialect.fromEntityManager(em)));
                }
            }
        }
    }

    public static NativeSqlQueryBuilder nativeSqlQuery(String selectFrom) {
        return new NativeSqlQueryBuilder(selectFrom);
    }
}
