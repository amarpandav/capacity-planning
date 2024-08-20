package com.ubs.cpt.infra.query.util;

/**
 *
 */
class Expressions {

    /**
     * Sometimes rather than taking Strings as expressions we'd like to use different types. In the end, however,
     * we need Strings, so we need something that converts from ExpressionT to String.
     */
    static interface ExpressionConverter<ExpressionT> {
        public String asString(ExpressionT expression);
    }

    /**
     * Identity converter
     */
    static class StringExpressionConverter implements ExpressionConverter<String> {
        public String asString(String expression) {
            return expression;
        }
    }

    /**
     * Converter that uses the alias defined by a selection column to replace it in order by expressions.
     */
    static class AliasSelectionExpressionConverter implements ExpressionConverter<SelectionUtils.SelectionColumn> {
        @Override
        public String asString(SelectionUtils.SelectionColumn column) {
            return column.getAlias();
        }
    }

    /**
     * Converter that uses the expression defined by a selection column to replace it in order by expressions.
     */
    static class ExpressionSelectionExpressionConverter implements ExpressionConverter<SelectionUtils.SelectionColumn> {
        @Override
        public String asString(SelectionUtils.SelectionColumn column) {
            return column.getExpression();
        }
    }

}
