package com.ubs.cpt.service.searchparams;


import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserKey;
import com.ubs.cpt.infra.search.DefaultSearchParameters;
import com.ubs.cpt.infra.search.SortBy;

import java.util.Optional;

/**
 * @author Amar Pandav
 */
public class UserSearchParameters extends DefaultSearchParameters<User> {

    //private String gpin;
    private UserKey userKey;
    private String name;
    private String jobTitle;
    private String country;

    public UserSearchParameters() {
        getSortBy().add(new SortBy("name", SortBy.SortDirection.ASCENDING));
    }

    public Optional<UserKey> getUserKey() {
        return Optional.ofNullable(userKey);
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getJobTitle() {
        return Optional.ofNullable(jobTitle);
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Optional<String> getCountry() {
        return Optional.ofNullable(country);
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public UserSearchParameters withUserKey(UserKey userKey) {
        setUserKey(userKey);
        return this;
    }

    public UserSearchParameters withName(String name) {
        setName(name);
        return this;
    }

    public UserSearchParameters withJobTitle(String jobTitle) {
        setJobTitle(jobTitle);
        return this;
    }

    public UserSearchParameters withCountry(String country) {
        setCountry(country);
        return this;
    }
}
