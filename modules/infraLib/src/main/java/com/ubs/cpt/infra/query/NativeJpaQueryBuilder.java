package com.ubs.cpt.infra.query;

import com.ubs.cpt.infra.datetime.CoreDateTimeUtils;
import com.ubs.cpt.infra.search.DefaultSearchParameters;
import com.ubs.cpt.infra.util.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.ubs.cpt.infra.util.CollectionUtils.transform;

/**
 * <p>Special JPA query builder that uses the EntityManager API to create native SQL query objects. If
 * you are using IntelliJ as your IDE, I would highly recommend you to set Language Injection settings
 * so that the IDE recognizes you are using SQL when passing queries to the constructor of this class.
 * In fact these Language Injection settings are one of the big reasons why I wanted to create different
 * query builders, because there is only one constructor I can use - which I can't configure for both
 * JPA QL and SQL. After all, one could have simply created two buildQuery methods - the normal one
 * and another for native queries.</p>
 *
 * @author Amar Pandav
 */
public class NativeJpaQueryBuilder<T> extends JpaQueryBuilder {

    // ------------------------------------------ Constructors

    public NativeJpaQueryBuilder(EntityManager em, String query) {
        super(em, query);
    }

    public NativeJpaQueryBuilder(EntityManager em, String query, boolean appendWhere) {
        super(em, query, appendWhere);
    }

    // ------------------------------------------ Factory methods

    public static JpaQueryBuilder nativeQueryBuilder(EntityManager em, String query) {
        return new NativeJpaQueryBuilder(em, query);
    }

    // ------------------------------------------ JpaQueryBuilder methods

    @Override
    protected Query createQuery(EntityManager em, String queryString) {
        return em.createNativeQuery(queryString);
    }

    @Override
    public NativeJpaQueryBuilder set(String parameterName, Object parameterValue) {
        parameterValue = convertParameterIfNecessary(parameterValue);
        super.set(parameterName, parameterValue);
        return this;
    }

    public List<T> getResultList() {
        List<Object[]> rows = QueryBuilderUtils.getResultList(this);
        return transformIfNeeded(rows);
    }

    public List<T> getPagingResultList(Integer from, Integer pageSize) {
        List<Object[]> rows = super.getPagingResultList(from, pageSize);
        return transformIfNeeded(rows);
    }

    public List<T> getPagingResultList(DefaultSearchParameters searchParameters) {
        List<Object[]> rows = QueryBuilderUtils.getPagingResultList(this, searchParameters);
        return transformIfNeeded(rows);
    }

    private List<T> transformIfNeeded(List<Object[]> queryResult) {
        CollectionUtils.Transformer transformer = getTransformer();
        if (transformer != null) {
            return transform(queryResult, TransformationSource.with(getTransformer()));
        } else {
            return (List<T>) queryResult;
        }
    }

    public CollectionUtils.Transformer<TransformationSource, T> getTransformer() {
        return null;
    }

    public static Object convertParameterIfNecessary(Object parameterValue) {
        if (parameterValue instanceof Enum<?>) {
            // cannot set enum value on native query, need to use name()
            parameterValue = ((Enum<?>) parameterValue).name();
        } else if (parameterValue instanceof Boolean) {
            // cannot set boolean instance, need to set 1 or 0
            parameterValue = ((Boolean) parameterValue) ? 1 : 0;
        } else if (parameterValue instanceof LocalDate) {
            // cannot use joda classes in native queries, need to use java.sql pendants
            parameterValue = CoreDateTimeUtils.javaSqlDate((LocalDate) parameterValue);
        } else if (parameterValue instanceof DateTime) {
            // cannot use joda classes in native queries, need to use java.sql pendants
            parameterValue = CoreDateTimeUtils.toTimestamp((DateTime) parameterValue);
        } else if (parameterValue instanceof Object[]) {
            Object[] parameterValues = (Object[]) parameterValue;

            List<Object> convertedParameterValues = new ArrayList<Object>(); // Doesn't matter much which collection we're using here
            for (Object rawParameterValue : parameterValues) {
                convertedParameterValues.add(convertParameterIfNecessary(rawParameterValue));
            }

            parameterValue = convertedParameterValues;
        } else if (parameterValue instanceof Iterable) {
            Iterable<?> parameterValues = (Iterable<?>) parameterValue;

            List<Object> convertedParameterValues = new ArrayList<Object>(); // Doesn't matter much which collection we're using here
            for (Object rawParameterValue : parameterValues) {
                convertedParameterValues.add(convertParameterIfNecessary(rawParameterValue));
            }

            parameterValue = convertedParameterValues;
        }

        return parameterValue;
    }

}

