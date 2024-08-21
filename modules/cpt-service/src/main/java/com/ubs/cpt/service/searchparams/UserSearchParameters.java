package com.ubs.cpt.service.searchparams;


import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.search.DefaultSearchParameters;
import com.ubs.cpt.infra.search.SortBy;

/**
 * @author Amar Pandav
 */
public class UserSearchParameters extends DefaultSearchParameters<User> {

    private String name;

    private String gpin;

    public UserSearchParameters() {
        getSortBy().add(new SortBy("name", SortBy.SortDirection.ASCENDING));
    }

    public String getGpin() {
        return gpin;
    }

    public void setGpin(String gpin) {
        this.gpin = gpin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
