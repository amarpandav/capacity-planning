package com.ubs.cpt.infra.query.util;

import com.ubs.cpt.infra.query.QueryBuilder;
import com.ubs.cpt.infra.util.SearchCriteriaUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.ubs.cpt.infra.util.CollectionUtils.nullSafeIterable;

/**
 * Encapsulates the logic for filtering based on search criteria in Mangos. All you need to do is to configure
 * one of these builders and then apply the logic to some query builder.
 *
 * @author Amar Pandav
 */
public class SearchCriteriaFilterBuilder<ExpressionT> {

    /**
     * All of these filter expressions will be put together in a huge disjunction.
     */
    private final List<String> filterExpressions = newArrayList();

    /**
     * So that we don't have to use raw strings
     */
    private final Expressions.ExpressionConverter<ExpressionT> expressionConverter;

    // ------------------------------------------ Constructors

    // You'll have to use the factory methods
    private SearchCriteriaFilterBuilder(Expressions.ExpressionConverter<ExpressionT> expressionConverter) {
        this.expressionConverter = expressionConverter;
    }

    public static SearchCriteriaFilterBuilder<String> newSearchCriteriaBuilder() {
        return new SearchCriteriaFilterBuilder<String>(new Expressions.StringExpressionConverter());
    }

    public static SearchCriteriaFilterBuilder<SelectionUtils.SelectionColumn> newExpressionSearchCriteriaFilterBuilder() {
        return new SearchCriteriaFilterBuilder<SelectionUtils.SelectionColumn>(new Expressions.ExpressionSelectionExpressionConverter());
    }

    public static SearchCriteriaFilterBuilder<SelectionUtils.SelectionColumn> newAliasSearchCriteriaFilterBuilder() {
        return new SearchCriteriaFilterBuilder<SelectionUtils.SelectionColumn>(new Expressions.AliasSelectionExpressionConverter());
    }

    // ------------------------------------------ Public methods

    public SearchCriteriaBuilderTarget consider(ExpressionT expression) {
        checkNotNull(expression, "The given expression must not be null.");
        return new SearchCriteriaBuilderTarget(expression);
    }

    public SearchCriteriaFilter build() {
        if (filterExpressions.isEmpty()) {
            throw new IllegalStateException(
                    "Please configure at least one filter expression ..");
        }

        return new SearchCriteriaFilter(newArrayList(filterExpressions));
    }

    /**
     * Convenience method that immediately applies this search criteria filter to a query builder. The build()
     * method has been implemented so that you don't have to configure the builder every single time you want
     * to apply it - once in the constructor would be sufficient and you could re-use that instance later on.
     * <p>
     * However, usually we don't really do that, which is why this method has been introduced.
     */
    public void applyTo(QueryBuilder queryBuilder, String searchCriteria) {
        build().applyTo(queryBuilder, searchCriteria);
    }

    public void applyToEscaped(QueryBuilder queryBuilder, String searchCriteria) {
        build().escapeLike().applyTo(queryBuilder, searchCriteria);
    }

    // ------------------------------------------ Utility classes

    public class SearchCriteriaBuilderTarget {
        private final ExpressionT expression;

        private SearchCriteriaBuilderTarget(ExpressionT expression) {
            this.expression = expression;
        }

        // -------------------------------------- Builder methods

        /**
         * Indicates that we should just include the previous expression as a string in our search criteria filter.
         * No conversion is needed, you can use the 'like' operator on that column directly (i.e. this only works
         * if that is actually correct, you can use 'like' with the column/expression you specified).
         */
        public SearchCriteriaFilterBuilder<ExpressionT> asString() {
            filterExpressions.add("lower(" + expressionConverter.asString(expression) + ")");
            return SearchCriteriaFilterBuilder.this;
        }

        /**
         * Indicates that we should include the previous expression as a date string in our search criteria filter,
         * i.e. we're assuming that the previous expression refers to a date and we'll convert that to a string using
         * predefined database functions.
         */
        public SearchCriteriaFilterBuilder<ExpressionT> asDateString() {
            filterExpressions.add("lower(to_char(" + expressionConverter.asString(expression) + ", 'DD MON YYYY'))");
            return SearchCriteriaFilterBuilder.this;
        }

    }

    /**
     * An instance of this class knows how to apply search criteria filter logic to a given query builder. All you
     * need to do is to configure the filter expressions beforehand (i.e. provide a list of expressions that will
     * be included in the disjunction for each search criterion).
     *
     * @author Bernhard Huemer
     */
    public static class SearchCriteriaFilter {

        private final List<String> filterExpressions;

        private boolean escape = false;

        //we make this static as sometimes a unique CNT might be needed (using several builders for one statement)
        private static int CNT = 0;

        private SearchCriteriaFilter(List<String> filterExpressions) {
            this.filterExpressions = filterExpressions;
        }

        // -------------------------------------- Public methods

        public SearchCriteriaFilter escapeLike() {
            escape = true;
            return SearchCriteriaFilter.this;
        }

        /**
         * Adds the filter conditions to the given query builder. Only if needed though, i.e. if there's actually
         * some search criteria.
         */
        public QueryBuilder applyTo(QueryBuilder queryBuilder, String searchCriteria) {
            for (String criteria : nullSafeIterable(SearchCriteriaUtil.splitSearchCriteria(searchCriteria))) {
                if (!StringUtils.isEmpty(criteria)) {
                    CNT = CNT + 1;
                    queryBuilder.and(QueryBuilder.brackets(
                            newDisjunctionFor("searchCrit" + CNT, criteria)
                    ));
                }
            }

            return queryBuilder;
        }

        /**
         * The whole filter will look something like ..
         * <p>
         * (column1 like :searchCrit1 or column2 like :searchCrit1 or column3 like :searchCrit1) and
         * (column1 like :searchCrit2 or column2 like :searchCrit2 or column3 like :searchCrit2) and
         * (column1 like :searchCrit3 or column2 like :searchCrit3 or column3 like :searchCrit3)
         * <p>
         * .. and this method builds one of these disjunctions in the overall conjuction.
         */
        private QueryBuilder newDisjunctionFor(String criteriaName, String criteria) {
            String escapeSign = "";
            if (escape) {
                //for now we just escape the %
                criteria = criteria.replaceAll("%", "/%%");
                escapeSign = " escape '/' ";
            }

            QueryBuilder disjunction = QueryBuilder.newQuery();
            disjunction
                    .set(criteriaName, '%' + StringUtils.lowerCase(criteria) + '%');

            for (String filterExpression : filterExpressions) {
                disjunction.or("(" + filterExpression + " like :" + criteriaName + escapeSign + ")");
            }

            return disjunction;
        }

        //needed in JUnits
        public static int getCNT() {
            return CNT;
        }
    }


}
