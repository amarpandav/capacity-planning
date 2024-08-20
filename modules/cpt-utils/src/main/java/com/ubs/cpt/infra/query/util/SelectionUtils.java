package com.ubs.cpt.infra.query.util;


import static com.ubs.cpt.infra.util.CollectionUtils.nullSafeIterable;

/**
 *
 */
public class SelectionUtils {

    public static String selection(SelectionColumn... selectionColumns) {
        StringBuilder selection = new StringBuilder();

        for (SelectionColumn selectionColumn : nullSafeIterable(selectionColumns)) {
            if (selection.length() > 0) {
                selection.append(", ");
            }

            selection
                    .append(selectionColumn.getExpression())
                    .append(" as ")
                    .append(selectionColumn.getAlias());
        }

        return selection.toString();
    }

    // looks ugly, but with Guava it's not much prettier ..
    public static String joinAliases(SelectionColumn... selectionColumns) {
        StringBuilder selection = new StringBuilder();

        for (SelectionColumn selectionColumn : nullSafeIterable(selectionColumns)) {
            if (selection.length() > 0) {
                selection.append(", ");
            }

            selection
                    .append(selectionColumn.getAlias());
        }

        return selection.toString();
    }

    public static String joinExpressions(SelectionColumn... selectionColumns) {
        StringBuilder selection = new StringBuilder();

        for (SelectionColumn selectionColumn : nullSafeIterable(selectionColumns)) {
            if (selection.length() > 0) {
                selection.append(", ");
            }

            selection
                    .append(selectionColumn.getExpression());
        }

        return selection.toString();
    }

    public static interface SelectionColumn {
        public String getExpression();

        public String getAlias();
    }

}
