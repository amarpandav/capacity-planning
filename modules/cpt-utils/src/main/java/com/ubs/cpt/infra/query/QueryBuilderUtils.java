package com.ubs.cpt.infra.query;

import com.ubs.cpt.infra.exception.DatabaseException;
import com.ubs.cpt.infra.search.DefaultSearchParameters;
import com.ubs.cpt.infra.util.JpaUtils;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.List;

/**
 * Utility functions that shouldn't go into the QueryBuilder class itself, because they're more specific for
 * Mangos services - the QueryBuilder class shouldn't have any dependency on Mangos-specific things. In this
 * case it would be the search parameters, for example.
 *
 * @author Amar Pandav
 */
public final class QueryBuilderUtils {

    // No need to instantiate this class
    private QueryBuilderUtils() {
    }

    /**
     * Just so that we don't have to add these @SuppressWarnings annotations every where ..
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getResultList(JpaQueryBuilder queryBuilder) {
        try {
            Query query = queryBuilder.buildQuery();
            JpaUtils.setReadOnlyHint(query);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw new DatabaseException(
                    "An error occurred while evaluating the query '" + queryBuilder.getQuery()
                            + "' with the parameters '" + queryBuilder.getParameters() + "'.", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getPagingResultList(JpaQueryBuilder queryBuilder, DefaultSearchParameters searchParameters) {
        try {
            Query query = queryBuilder.buildPagingQuery(searchParameters.getFrom(), searchParameters.getPageSize());
            JpaUtils.setReadOnlyHint(query);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw new DatabaseException(
                    "An error occurred while evaluating the query '" + queryBuilder.getQuery()
                            + "' with the parameters '" + queryBuilder.getParameters()
                            + "' and the search parameters '" + searchParameters + "'.", ex);
        }
    }

    /**
     * Assumes that the given query is something like "select count(*) from" - be it native or JPA QL -
     * and returns that single value as an integer, i.e. it effectively counts the results.
     */
    public static int countResults(JpaQueryBuilder queryBuilder) {
        try {
            return ((Number) queryBuilder
                    .buildQuery()
                    .getSingleResult()).intValue();
        } catch (PersistenceException ex) {
            throw new DatabaseException(
                    "An error occurred while counting results for the query '" + queryBuilder.getQuery()
                            + "' with the parameters '" + queryBuilder.getParameters() + "'.", ex);
        }
    }

}
