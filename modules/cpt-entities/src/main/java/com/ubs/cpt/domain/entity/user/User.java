package com.ubs.cpt.domain.entity.user;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.base.FieldConstants;
import jakarta.persistence.*;

/**
 * cpt_user table.
 *
 * @author Amar Pandav
 */
@Entity
@Table(
        name = User.TABLE_NAME
)
public class User extends BaseEntity<User> {
    public static final String TABLE_NAME = "cpt_user";

    public static final class Columns {
        public static final String NAME = "name";
        public static final String GPIN = "gpin";
        public static final String JOB_TITLE = "job_title";
        public static final String COUNTRY = "country";
    }

    @Column(name = Columns.NAME, length = FieldConstants.NAME, nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(name = Columns.GPIN, column = @Column(name = UserKey.Columns.gpin, length = FieldConstants.NAME, nullable = false))
    private UserKey key;

    @Column(name = Columns.JOB_TITLE, length = FieldConstants.GENERAL_50, nullable = true)
    private String jobTitle;

    /**
     * Name of the user
     */
    @Column(name = Columns.COUNTRY, length = FieldConstants.GENERAL_50, nullable = true)
    private String country;

    protected User() {// required by JPA
    }

    public User(String name, UserKey key) {
        this(name, key, null, null);
    }

    public User(String name, UserKey key, String jobTitle, String country) {
        this.name = name;
        this.key = key;
        this.jobTitle = jobTitle;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public UserKey getKey() {
        return key;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCountry() {
        return country;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
