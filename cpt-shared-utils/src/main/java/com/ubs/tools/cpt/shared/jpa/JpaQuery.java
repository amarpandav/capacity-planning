package com.ubs.tools.cpt.shared.jpa;

import com.ubs.tools.cpt.shared.sql.SqlDialect;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public record JpaQuery(Function<SqlDialect, String> selectQuerySupplier, Collection<Param> params) {
    public record Param(String name, Object value, IncludeType includeType) {
        enum IncludeType {
            ALWAYS,
            NOT_NULL
        }
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    public Query build(EntityManager em, SqlDialect dialect) {
        var query = em.createNativeQuery(selectQuerySupplier.apply(dialect), Tuple.class);

        params.forEach(param -> {
            boolean notNullAndSet = param.includeType() == Param.IncludeType.NOT_NULL && param.value() != null;
            boolean alwaysInclude = param.includeType() == Param.IncludeType.ALWAYS;

            if (alwaysInclude || notNullAndSet) {
                query.setParameter(param.name(), param.value());
            }
        });

        return query;
    }

    public Query build(EntityManager em) {
        return build(em, SqlDialect.fromEntityManager(em));
    }

    @SuppressWarnings("unchecked")
    public Stream<Tuple> getResultStream(EntityManager em, OffsetLimit offsetLimit, SqlDialect dialect) {
        return build(em, dialect)
            .setFirstResult(offsetLimit.offset().orElse(0))
            .setMaxResults(offsetLimit.limit().orElse(Integer.MAX_VALUE))
            .getResultStream();
    }

    public Stream<Tuple> getResultStream(EntityManager em) {
        return getResultStream(em, OffsetLimit.none(), SqlDialect.fromEntityManager(em));
    }

    @SuppressWarnings("unchecked")
    public List<Tuple> getResultList(EntityManager em, OffsetLimit offsetLimit, SqlDialect dialect) {
        return build(em, dialect).getResultList();
    }

    public List<Tuple> getResultList(EntityManager em) {
        return getResultList(em, OffsetLimit.none(), SqlDialect.fromEntityManager(em));
    }

    public Object getSingleResult(EntityManager em) {
        return build(em).getSingleResult();
    }
}
