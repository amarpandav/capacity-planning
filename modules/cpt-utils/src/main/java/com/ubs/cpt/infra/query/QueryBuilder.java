package com.ubs.cpt.infra.query;

import com.ubs.cpt.infra.search.SortBy;
import com.ubs.cpt.infra.util.JpaUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.ubs.cpt.infra.util.ListSplitter.splitCollection;

/**
 * <p>Utility class that allows me to build queries dynamically. This is not tied to the concept of JPA
 * queries though. It comes with almost no bells and whistles as I only want it to keep track of
 * parameters that I have specified. Depending on whether they are specified or not, conditions need to
 * be added as well.</p>
 *
 * @author Amar Pandav
 */
public class QueryBuilder {

    /**
     * The query that we are building in its textual form.
     */
    private final StringBuilder query;

    /**
     * A table that includes all the parameters that we have specified.
     */
    private final Map<String, Object> parameters = new HashMap<String, Object>();

    private boolean appendWhere;

    // Note that this version of a query builder eagerly appends everything to the main query.
    // We can't keep three different StringBuilders for conditions, order bys and group bys
    // as a result of that. The reasoning behind this is that the getQuery() method should
    // always return the most recent version of the query, one that can be modified as well.
    // Anyone who uses these query builders can therefore modify the query as he / she wishes. It
    // is not prevented, by design. Why do we do this? Well, for more complex queries we
    // can't really dictate what goes where. For example, there's one case where I have a
    // sub-query in the condition and that's the one where I want to add conditions
    // dynamically. (select a from A a where exists (select 1 from B b /* add conditions here */)).
    // You can't really do this if you're not allowed to modify the query in any way (e.g. you
    // may need to append parentheses).

    /**
     * Boolean flag that indicates we have added a condition already.
     */
    private boolean haveWeAddedConditions = false;

    /**
     * Boolean flag that indicates we have added something with an "OR" already.
     * Usually you only want to have these in nested query builders.
     */
    private boolean haveWeAddedOrs = false;

    /**
     * Boolean flag that indicates we have added order bys already.
     */
    private boolean haveWeAddedOrderBys = false;

    /**
     * Boolean flag that indicates we have added group bys already.
     */
    private boolean haveWeAddedGroupBys = false;

    enum SQLOperator {
        LIKE,
        STARTSWITH,
        ENDSWITH;
    }
    // ------------------------------------------ Constructors

    public QueryBuilder() {
        this("");
    }

    /**
     * <p>Initializes the query builder with the initial query. This should include all the FROMs, the JOINs
     * and whatever else along those lines. Some conditions might be unconditional (as in, you always have
     * a where statement with some clauses regardless of what parameters have been specified), in which
     * case you might want to specify that here as well.</p>
     *
     * @param query the initial query
     */
    public QueryBuilder(String query) {
        this(query, query != null && !query.contains("where"));
    }

    public QueryBuilder(String query, boolean appendWhere) {
        if (query == null) {
            throw new IllegalArgumentException("The initial query must not be null.");
        }

        this.query = new StringBuilder(query);
        this.appendWhere = appendWhere;
    }

    // ------------------------------------------ Public methods

    /**
     * <p>Allows you to set one parameter in this query builder to a specific value. You can call this method as
     * many times as you want as long as the value stays the same as well (i.e. you can't set two different
     * values).</p>
     *
     * @param parameterName  the name of the parameter
     * @param parameterValue the value of the parameter
     * @return this query builder again, to enable method chaining
     */
    public QueryBuilder set(String parameterName, Object parameterValue) {
        if (!parameters.containsKey(parameterName)) {
            parameters.put(parameterName, parameterValue);
        } else {
            Object previousValue = parameters.get(parameterName);
            if (!parameterValue.equals(previousValue)) {
                throw new IllegalArgumentException(
                        "The parameter name '" + parameterName + "' you gave for this condition " +
                                "has been used once already. This doesn't look right as you would be overriding the previous value, which" +
                                "was '" + parameters.get(parameterName) + "'. The new value would be '" + parameterValue + "'.");
            }
        }

        return this;
    }

    public QueryBuilder set(String parameterName, Object parameterValue, Condition condition) {
        if (condition.isTrue()) {
            set(parameterName, parameterValue);
        }

        return this;
    }

    protected QueryBuilder setAll(QueryBuilder sourceQueryBuilder) {
        for (Map.Entry<String, Object> parameter : sourceQueryBuilder.getParameters().entrySet()) {
            set(parameter.getKey(), parameter.getValue());
        }

        return this;
    }

    public QueryBuilder append(String statement) {
        query.append(statement);
        return this;
    }

    public QueryBuilder append(String statement, Condition condition) {
        if (condition.isTrue()) {
            append(statement);
        }

        return this;
    }

    public QueryBuilder append(QueryBuilder statement) {
        append(statement.buildTextualQuery());
        return setAll(statement);
    }

    public QueryBuilder append(QueryBuilder statement, Condition condition) {
        if (condition.isTrue()) {
            append(statement.buildTextualQuery());
            return setAll(statement);
        }

        return this;
    }

    public QueryBuilder or(String condition) {
        if (haveWeAddedOrs) {
            query.append(" or ");
        }

        haveWeAddedOrs = true;
        query.append(condition);

        return this;
    }

    public QueryBuilder or(QueryBuilder conditionClause) {
        or(conditionClause.buildTextualQuery());
        return setAll(conditionClause);
    }

    public QueryBuilder or(String conditionClause, Condition condition) {
        if (condition.isTrue()) {
            or(conditionClause);
        }

        return this;
    }

    public QueryBuilder or(QueryBuilder conditionClause, Condition condition) {
        if (condition.isTrue()) {
            or(conditionClause);
        }

        return this;
    }

    public QueryBuilder or(String condition, String parameterName, Object parameterValue) {
        if (parameterValue != null) {
            // Strings are being treated differently. If an empty string is being given, we treat it
            // as if the value was null to begin with - meaning that we ignore that condition altogether.
            if (parameterValue instanceof String) {
                boolean emptyString = ((String) parameterValue).isEmpty();
                if (emptyString && filterEmptyStrings()) {
                    return this;
                }
            }

            set(parameterName, parameterValue);
            or(condition);
        }

        return this;
    }

    public QueryBuilder or(String conditionClause, String parameterName, Object parameterValue, Condition condition) {
        if (condition.isTrue()) {
            or(conditionClause, parameterName, parameterValue);
        }

        return this;
    }

    public QueryBuilder and(String condition) {
        // Make sure that we "and" all these conditions together. Just the first one doesn't need to be "and"-ed.
        if (haveWeAddedConditions) {
            query.append(" and ");
        } else {
            // Only append "where"s lazily, i.e. when we actually need them. It might be that a where has
            // been added already though, in which case this is not needed.
            if (appendWhere) {
                query.append(" where ");
            } else {
                // If this is the first condition and we know that we shouldn't append a where, possibly
                // because one has been added already, well than use an "and" instead.
                query.append(" and ");
            }
        }

        haveWeAddedConditions = true;
        query.append(condition);

        return this;
    }

    public QueryBuilder and(String conditionClause, Condition condition) {
        if (condition.isTrue()) {
            and(conditionClause);
        }

        return this;
    }

    /*
     * Executes the given condition as an "in" query which is also safe under oracle conditions.
      * Means you will never get the >1000 values issue for "in" clauses in oracle!
     */
    public QueryBuilder andSplitted(String conditionClause, String parameterName, List<? extends Object> parameterValues) {
        if (parameterValues.size() <= 1000) { //oracle restriction
            and(conditionClause + " in (:" + parameterName + ")", parameterName, parameterValues);
        } else {
            Iterator<? extends List<?>> slicer = splitCollection(parameterValues);
            int i = 0;
            StringBuilder orCondition = new StringBuilder("(");
            while (slicer.hasNext()) {
                List<?> currentSlice = slicer.next();
                set(parameterName + i, currentSlice);

                orCondition.append(conditionClause)
                        .append(" in (:")
                        .append(parameterName)
                        .append(i)
                        .append(")");

                if (slicer.hasNext()) {
                    orCondition = orCondition.append(" or ");
                }
                i++;
            }

            orCondition.append(")");

            and(orCondition.toString());
        }

        return this;
    }

    /**
     * <p>Adds a conditional clause to the query and keeps track of the given parameter. This parameter should
     * obviously appear somewhere in the clause, possibly multiple times.</p>
     *
     * @param condition      the conditional clause you want to add
     * @param parameterName  the parameter name
     * @param parameterValue the parameter value
     * @return this query builder to allow for a fluent API
     */
    public QueryBuilder and(String condition, String parameterName, Object parameterValue) {
        if (parameterValue != null) {
            // Strings are being treated differently. If an empty string is being given, we treat it
            // as if the value was null to begin with - meaning that we ignore that condition altogether.
            if (parameterValue instanceof String) {
                boolean emptyString = ((String) parameterValue).isEmpty();
                if (emptyString && filterEmptyStrings()) {
                    return this;
                }
            }

            set(parameterName, parameterValue);
            and(condition);
        }

        return this;
    }


    public QueryBuilder andStartsWith(String condition, String parameterName, Object parameterValue) {
        return andLike(condition, parameterName, parameterValue, SQLOperator.STARTSWITH);
    }

    public QueryBuilder andLike(String condition, String parameterName, Object parameterValue) {
        return andLike(condition, parameterName, parameterValue, SQLOperator.LIKE);
    }

    public QueryBuilder andEndsWith(String condition, String parameterName, Object parameterValue) {
        return andLike(condition, parameterName, parameterValue, SQLOperator.ENDSWITH);
    }

    public QueryBuilder andLike(String condition, String parameterName, Object parameterValue, SQLOperator operator) {
        if (parameterValue != null) {
            // Strings are being treated differently. If an empty string is being given, we treat it
            // as if the value was null to begin with - meaning that we ignore that condition altogether.
            // Whilst in theory, it is possible to use a different method with a different signature
            // (andLike(String, String, String) so that we don't have to add this runtime check, it would
            // not guarantee this behaviour all the time. One simply needs to invoke this method like
            // "andLike(condition, parameterName, (Object) "")" and suddenly this class would behave
            // differently. In order to avoid such inconsistent outcomes, this runtime check
            // has been added.
            if (parameterValue instanceof String) {
                boolean emptyString = ((String) parameterValue).isEmpty();
                if (emptyString) {
                    return this;
                }
                String newParamValue = parameterValue.toString();
                if (operator == SQLOperator.LIKE) {
                    newParamValue = prepareManualWildcardLikeParameter(newParamValue);
                } else if (operator == SQLOperator.STARTSWITH) {
                    newParamValue = prepareManualWildcardStartsWithParameter(newParamValue);
                } else if (operator == SQLOperator.ENDSWITH) {
                    newParamValue = prepareManualWildcardEndsWithParameter(newParamValue);
                }

                /*return and(condition + " ESCAPE '\\'", parameterName, newParamValue); // Escape gives sql error */
                return and(condition, parameterName, newParamValue);
            }
        }

        return this;
    }

    public QueryBuilder and(String conditionClause, String parameterName, Object parameterValue, Condition condition) {
        if (condition.isTrue()) {
            and(conditionClause, parameterName, parameterValue);
        }

        return this;
    }

    public QueryBuilder and(QueryBuilder conditionClause) {
        and(conditionClause.buildTextualQuery());
        return setAll(conditionClause);
    }

    public QueryBuilder and(QueryBuilder conditionClause, Condition condition) {
        if (condition.isTrue()) {
            and(conditionClause.buildTextualQuery());
            return setAll(conditionClause);
        }

        return this;
    }

    protected static String prepareManualWildcardStartsWithParameter(String parameter) {
        return (replaceManualWildcards(JpaUtils.escapeLikeParameter(parameter)) + "%");
    }

    protected static String prepareManualWildcardEndsWithParameter(String parameter) {
        return ("%" + replaceManualWildcards(JpaUtils.escapeLikeParameter(parameter)));
    }

    protected static String prepareManualWildcardLikeParameter(String parameter) {
        return ("%" + replaceManualWildcards(JpaUtils.escapeLikeParameter(parameter)) + "%");
    }

    protected static String replaceManualWildcards(String parameter) {
        return parameter.replaceAll("\\*", "%");
    }

    /**
     * <p>In case you have some conditions like "where exists (select 1 ..)" this might be quite useful. The whole
     * "select 1" clause can be built as a sub-query.</p>
     */
    public QueryBuilder and(QueryBuilder conditionClause, String parameterName, Object parameterValue, Condition condition) {
        if (condition.isTrue()) {
            and(conditionClause.buildTextualQuery(), parameterName, parameterValue);
            return setAll(conditionClause);
        }

        return this;
    }

    // TODO: Eventually ands that appear directly in where clauses should be renamed to use "where" instead .. so that
    // we can distinguish between "ands" where the query builder is supposed to add "where " automatically and the
    // conditions (e.g. in sub-queries) that don't add these "where"s automatically
    public QueryBuilder where(String condition) {
        return and(condition);
    }

    public QueryBuilder where(QueryBuilder conditionBuilder) {
        return and(conditionBuilder);
    }

    public QueryBuilder where(String conditionClause, Condition condition) {
        return and(conditionClause, condition);
    }

    public QueryBuilder where(String condition, String parameterName, Object parameterValue) {
        return and(condition, parameterName, parameterValue);
    }

    public QueryBuilder where(String conditionClause, String parameterName, Object parameterValue, Condition condition) {
        return and(conditionClause, parameterName, parameterValue, condition);
    }

    private QueryBuilder orderBy(String orderByClause, boolean ascending) {
        if (!haveWeAddedOrderBys) {
            query.append(" order by ");
            haveWeAddedOrderBys = true;
        } else {
            query.append(", ");
        }

        query.append(orderByClause);
        if (!ascending) {
            query.append(" desc");
        }

        return this;
    }

    public QueryBuilder orderBy(String orderByClause, SortBy.SortDirection sortDirection) {
        return orderBy(orderByClause, sortDirection == SortBy.SortDirection.ASCENDING);
    }

    /**
     * <p>A convenience method for the order by function that allows for a more fluid API.</p>
     *
     * @param orderByClause the clause by which you want to order
     * @param ascending     whether it's ascending or not
     * @param condition     a condition that will be checked before considering this call
     * @return this query builder
     */
    public QueryBuilder orderBy(String orderByClause, boolean ascending, Condition condition) {
        if (condition.isTrue()) {
            orderBy(orderByClause, ascending);
        }

        return this;
    }

    public QueryBuilder orderBy(String orderByClause, SortBy.SortDirection sortDirection, Condition condition) {
        return orderBy(orderByClause, sortDirection, condition);
    }

    /**
     * <p>In case you want to order by sub-queries, this might be quite useful.</p>
     */
    private QueryBuilder orderBy(QueryBuilder orderByClause, boolean ascending, Condition condition) {
        if (condition.isTrue()) {
            orderBy(orderByClause.buildTextualQuery(), ascending);
            return setAll(orderByClause);
        }

        return this;
    }

    public QueryBuilder orderBy(QueryBuilder orderByClause, SortBy.SortDirection sortDirection, Condition condition) {
        return orderBy(orderByClause, sortDirection, condition);
    }

    public QueryBuilder groupBy(String groupByClause) {
        if (!haveWeAddedGroupBys) {
            query.append(" group by ");
            haveWeAddedGroupBys = true;
        } else {
            query.append(", ");
        }

        query.append(groupByClause).append(" ");

        return this;
    }

    /**
     * <p>Returns the query in its textual form. You can modify this if you wish. Changes will be kept.</p>
     *
     * @return the query in its textual form
     */
    public StringBuilder getQuery() {
        return query;
    }

    /**
     * <p>Returns the final query in its textual form. This is meant to be used when actually creating
     * whatever query objects you want to created.</p>
     *
     * @return the final query in its textual form
     */
    public String buildTextualQuery() {
        return getQuery().toString();
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    // ------------------------------------------ Object methods

    /**
     * So that we can use this query builder in exception messages and log files ..
     */
    @Override
    public String toString() {
        return "QueryBuilder[" +
                "query=" + query +
                ", parameters=" + parameters +
                ']';
    }

    // ------------------------------------------ Utility methods

    /**
     * Puts the given query in brackets / parentheses.
     */
    public static QueryBuilder brackets(QueryBuilder queryBuilder) {
        return new QueryBuilder()
                .append("(")
                .append(queryBuilder)
                .append(")");
    }

    public static QueryBuilder newQuery() {
        return new QueryBuilder();
    }

    public static QueryBuilder newQuery(String query) {
        return new QueryBuilder(query);
    }

    // In case you want to modify the behaviour for empty strings, override this method
    protected boolean filterEmptyStrings() {
        return true;
    }

    public static Condition ifTrue(boolean result) {
        if (result) {
            return Condition.TRUE;
        } else {
            return Condition.FALSE;
        }
    }

    public static Condition ifFalse(boolean result) {
        return ifTrue(!result);
    }

    public static <T> Condition ifEqual(T lhs, T rhs) {
        if (lhs == rhs) { // Both might be null
            return Condition.TRUE;
        }

        return ifTrue(lhs != null && lhs.equals(rhs));
    }

    /**
     * <p>Utility class that has been created to make use of type-safety as means to force users of this class
     * to use the fluent API that comes along with it. In theory it would be enough to pass a simple boolean
     * value, but then it's not as fluent when you're calling that method.</p>
     * <p>
     * <p>I know, it's weird .. and I might be overdoing it, but then I don't really care.</p>
     *
     * @author Amar Pandav
     *         Ya its clear, you did not cared about the project and its maintainability. We are the one who has to maintain this fuxxing code.
     * @author Bernhard Huemer
     */
    public static class Condition {

        private static final Condition TRUE = new Condition(true);
        private static final Condition FALSE = new Condition(false);

        /**
         * the previously evaluated boolean value
         */
        private boolean result;

        /**
         * <p>Initializes this condition with the boolean value that has been evaluated peviously already.</p>
         *
         * @param result the previously evaluated boolean value
         */
        private Condition(boolean result) {
            this.result = result;
        }

        // -------------------------------------- Public methods

        public Condition or(Condition other) {
            return ifTrue(this.isTrue() || other.isTrue());
        }

        public Condition and(Condition other) {
            return ifTrue(this.isTrue() && other.isTrue());
        }

        /**
         * <p>Simply returns whatever boolean value has been evaluated previously already. It can't be stressed
         * often enough. It seems complicated to do that, but it enables an incredibly fluent API.</p>
         *
         * @return
         */
        public boolean isTrue() {
            return result;
        }

    }

}
