package com.ubs.cpt.service.searchparams;


import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.search.DefaultSearchParameters;
import com.ubs.cpt.infra.search.SortBy;

/**
 * @author Amar Pandav
 */
public class UserSearchParameters extends DefaultSearchParameters<User> {

    //Could be fruit name etc.
    private String searchCriteria;

    public UserSearchParameters() {
        getSortBy().add(new SortBy("name", SortBy.SortDirection.ASCENDING));
    }


    public UserSearchParameters(String searchCriteria) {
        this();
        this.searchCriteria = searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }
}
