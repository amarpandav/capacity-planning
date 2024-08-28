package com.ubs.cpt.infra.query;

import com.ubs.cpt.infra.exception.DatabaseException;
import com.ubs.cpt.infra.search.DefaultSearchParameters;
import com.ubs.cpt.infra.util.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * <p>Extension to the plain query builder which knows how to construct JPA queries out of what you have specified. If
 * you are using IntelliJ as your IDE, I would highly recommend you to set Language Injection settings so that the IDE
 * recognizes you are using JPA QL when passing queries to the constructor of this class.</p>
 *
 * @author Amar Pandav
 */
public class JpaQueryBuilder<T> extends QueryBuilder {

    /**
     * The logger instance for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(JpaQueryBuilder.class);

    /*the count which is expected in case this query is used as a count query*/
    private Integer expectedUpdateCount = null;

    /**
     * The EntityManager referring to the AURA Core database
     */
    private final EntityManager em;

    // ------------------------------------------ Constructors

    public JpaQueryBuilder(EntityManager em, String query) {
        super(query);

        if (em == null) {
            throw new IllegalArgumentException("The given EntityManager must not be null.");
        }
        this.em = em;
    }

    public JpaQueryBuilder(EntityManager em, String query, boolean appendWhere) {
        super(query, appendWhere);

        if (em == null) {
            throw new IllegalArgumentException("The given EntityManager must not be null.");
        }
        this.em = em;
    }

    // ------------------------------------------ QueryBuilder methods

    // For some simple conditions it's annoying having to cast the type up again .. so we'll do it here.

    @Override
    public JpaQueryBuilder and(String condition) {
        return (JpaQueryBuilder) super.and(condition);
    }

    @Override
    public JpaQueryBuilder and(String conditionClause, Condition condition) {
        return (JpaQueryBuilder) super.and(conditionClause, condition);
    }

    @Override
    public JpaQueryBuilder and(String condition, String parameterName, Object parameterValue) {
        return (JpaQueryBuilder) super.and(condition, parameterName, parameterValue);
    }

    @Override
    public JpaQueryBuilder andLike(String condition, String parameterName, Object parameterValue) {
        return (JpaQueryBuilder) super.andLike(condition, parameterName, parameterValue);
    }

    @Override
    public JpaQueryBuilder andStartsWith(String condition, String parameterName, Object parameterValue) {
        return (JpaQueryBuilder) super.andStartsWith(condition, parameterName, parameterValue);
    }

    @Override
    public JpaQueryBuilder andEndsWith(String condition, String parameterName, Object parameterValue) {
        return (JpaQueryBuilder) super.andEndsWith(condition, parameterName, parameterValue);
    }

    @Override
    public JpaQueryBuilder and(String conditionClause, String parameterName, Object parameterValue, Condition condition) {
        return (JpaQueryBuilder) super.and(conditionClause, parameterName, parameterValue, condition);
    }

    // TODO: Eventually ands that appear directly in where clauses should be renamed to use "where" instead .. so that
    // we can distinguish between "ands" where the query builder is supposed to add "where " automatically and the
    // conditions (e.g. in sub-queries) that don't add these "where"s automatically
    public JpaQueryBuilder where(String condition) {
        return and(condition);
    }

    public JpaQueryBuilder where(QueryBuilder conditionBuilder) {
        return (JpaQueryBuilder) and(conditionBuilder);
    }

    public JpaQueryBuilder where(String conditionClause, Condition condition) {
        return and(conditionClause, condition);
    }

    public JpaQueryBuilder where(String condition, String parameterName, Object parameterValue) {
        return and(condition, parameterName, parameterValue);
    }

    public JpaQueryBuilder where(String conditionClause, String parameterName, Object parameterValue, Condition condition) {
        return and(conditionClause, parameterName, parameterValue, condition);
    }

    public QueryBuilder expectUpdateCount(int count) {
        this.expectedUpdateCount = count;
        return this;
    }

    // ------------------------------------------ Public methods

    public T getSingleResult() {
        List<T> resultList = getResultList();
        return JpaUtils.toSingleResult(resultList);
    }

    public List<T> getResultList() {
        return QueryBuilderUtils.getResultList(this);
    }

    public List<T> getPagingResultList(Integer from, Integer pageSize) {
        try {
            Query query = buildPagingQuery(from, pageSize);
            JpaUtils.setReadOnlyHint(query);
            return query.getResultList();
        } catch (PersistenceException ex) {
            throw new DatabaseException(
                    "An error occurred while evaluating the query '" + getQuery()
                            + "' with the parameters '" + getParameters() + "'.", ex);
        }
    }

    public List<T> getPagingResultList(DefaultSearchParameters searchParameters) {
        return QueryBuilderUtils.getPagingResultList(this, searchParameters);
    }

    /**
     * Assumes that the given query is something like "select count(*) from" - be it native or JPA QL -
     * and returns that single value as an integer, i.e. it effectively counts the results.
     */
    public int countResults() {
        return QueryBuilderUtils.countResults(this);
    }

    /**
     * Use this method rather than building the query yourself and executing it later on in case of update queries so
     * that you'll automatically capture the SQL/JQL query and the parameters too in exception messages.
     */
    public int executeUpdate() {
        try {
            int count = buildQuery().executeUpdate();
            if (expectedUpdateCount != null) {
                //let us check if the expected update count equals to the actual one
                if (expectedUpdateCount != count) {
                    throw new IllegalStateException(
                            String.format("Query did not update the expected amount of rows! Expected: %s, Actual %s ",
                                    expectedUpdateCount, count));
                }
            }

            return count;
        } catch (RuntimeException ex) {
            throw new PersistenceException(
                    "Could not execute the JPA query '" + getQuery() + "' with the parameters '" + getParameters() + "'.", ex);
        }
    }

    /**
     * <p>Builds and returns a JPA query out of all the information that you have specified up until now.</p>
     *
     * @return a JPA query out of all the information that you have specified up until now
     */
    public Query buildQuery() {
        try {
            Query query = createQuery(em, buildTextualQuery());
            populateQueryWithParameters(query);

            if (logger.isDebugEnabled()) {
                logger.debug("Built the query '" + buildTextualQuery() + "' and populated it " +
                        "with the parameters '" + getParameters() + "'.");
            }

            return query;
        } catch (PersistenceException ex) {
            throw new PersistenceException("Could not create a JPA query for the query '"
                    + getQuery() + "' with the parameters '" + getParameters() + "'.", ex);
        } catch (IllegalStateException ex) {
            throw new PersistenceException("Could not create a JPA query for the query '"
                    + getQuery() + "' with the parameters '" + getParameters() + "'.", ex);
        } catch (IllegalArgumentException ex) {
            throw new PersistenceException("Could not create a JPA query for the query '"
                    + getQuery() + "' with the parameters '" + getParameters() + "'.", ex);
        }
    }

    public Query buildPagingQuery(Integer from, Integer pageSize) {
        Query query = buildQuery();

        if (from != null && pageSize != null) {
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
        }

        return query;
    }

    // ------------------------------------------ Utility methods

    /**
     * <p>Utility method that actually creates the underlying JPA query. This method is overridden in
     * a subclass to allow for the case where one wants to create native queries using the EntityManager
     * API.</p>
     *
     * @param em          the EntityManager referring to the AURA Core database
     * @param queryString the query string for our query, usually JPA QL but may well be SQL as well
     * @return a new JPA query object
     */
    protected Query createQuery(EntityManager em, String queryString) {
        return em.createQuery(queryString);
    }

    /**
     * <p>Utility method that populates the given JPA query with all the parameters that have been specified
     * previously.</p>
     *
     * @param query the JPA query you want to populate with parameters
     * @return the query you have specified so that you can chain this method call
     */
    private Query populateQueryWithParameters(Query query) {
        if (getParameters() != null && getParameters().entrySet() != null) {
            for (Map.Entry<String, Object> parameter : getParameters().entrySet()) {
                query.setParameter(parameter.getKey(), parameter.getValue());
            }
        }

        return query;
    }

}
