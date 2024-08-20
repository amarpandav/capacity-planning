package com.ubs.cpt.infra.util;


import org.apache.commons.lang3.StringUtils;

/**
 * @author Amar Pandav
 */
public class SearchCriteriaUtil {

    public static final String SPLIT_BY_SPACE = "\\s+";
    public static final String SPLIT_BY_SEMI_COLON = ";";
    public static String[] splitSearchCriteria(String searchCriteria) {
        return split(searchCriteria, SPLIT_BY_SPACE);
    }

    public static String[] splitSearchCriteria(String searchCriteria, String splitByCharacter) {
        return split(searchCriteria, splitByCharacter);
    }

    private static String[] split(String searchCriteria, String splitByCharacter) {
        if (!StringUtils.isBlank(searchCriteria)) {
            String[] splits = searchCriteria.trim().split(splitByCharacter);
            //I saw we have a problem after the split with space and hence decided to trim those off.
            for (int i = 0; i < splits.length; i++) {
                splits[i] = splits[i].trim();
            }
            return splits;
        } else {
            return new String[0];
        }
    }
}
