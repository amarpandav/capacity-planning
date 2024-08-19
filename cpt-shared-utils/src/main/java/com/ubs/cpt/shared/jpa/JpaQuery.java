package com.ubs.cpt.shared.jpa;

import com.ubs.cpt.shared.sql.SqlDialect;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings({"rawtypes", "unchecked", "SqlSourceToSinkFlow"})
public record JpaQuery(Function<SqlDialect, String> selectQuerySupplier, Collection<Param> params) {
    public record Param(String name, Object value, IncludeType includeType) {
        enum IncludeType {
            ALWAYS,
            NOT_NULL
        }
    }

    public static final class QueryResult<T> {
        private final Query query;

        public QueryResult(Query query) {
            this.query = query;
        }

        public Stream<T> getResultStream(OffsetLimit offsetLimit) {
            return OffsetLimit.applyTo(query, offsetLimit).getResultStream();
        }

        public Stream<T> getResultStream() {
            return getResultStream(null);
        }

        public List<T> getResultList(OffsetLimit offsetLimit) {
            return OffsetLimit.applyTo(query, offsetLimit).getResultList();
        }
        public List<T> getResultList() {
            return getResultList(null);
        }

        public T getSingleResult(OffsetLimit offsetLimit) {
            try {
                return (T) OffsetLimit.applyTo(query, offsetLimit).getSingleResult();
            } catch (NoResultException ex) {
                return null;
            }
        }
        public T getSingleResult() {
            return getSingleResult(null);
        }
    }

    private QueryResult build(Query query) {
        params.forEach(param -> {
            boolean notNullAndSet = param.includeType() == Param.IncludeType.NOT_NULL && param.value() != null;
            boolean alwaysInclude = param.includeType() == Param.IncludeType.ALWAYS;

            if (alwaysInclude || notNullAndSet) {
                query.setParameter(param.name(), param.value());
            }
        });

        return new QueryResult(query);
    }

    public QueryResult<Tuple> buildNativeQuery(EntityManager em, SqlDialect dialect) {
        return build(em.createNativeQuery(selectQuerySupplier.apply(dialect), Tuple.class));
    }

    public QueryResult<Tuple> buildNativeQuery(EntityManager em) {
        return buildNativeQuery(em, SqlDialect.fromEntityManager(em));
    }

    public <T> QueryResult<T> buildJpaQuery(EntityManager em) {
        return build(em.createQuery(selectQuerySupplier().apply(SqlDialect.GENERIC)));
    }

    public <T> QueryResult<T> buildJpaQuery(EntityManager em, Class<T> resultClass) {
        return build(em.createQuery(selectQuerySupplier().apply(SqlDialect.GENERIC), resultClass));
    }
}
