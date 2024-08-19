package com.ubs.cpt.domain.entity.user;


import com.ubs.cpt.infra.domain.BaseEntity;
import com.ubs.cpt.infra.domain.base.FieldConstants;

import jakarta.persistence.*;

/**
 * User table.
 *
 * @author Amar Pandav
 */
@Entity
@Table(
        name = User.TABLE_NAME
)
public class User extends BaseEntity<User> {
    public static final String TABLE_NAME = "user";

    public static final class Columns {
        public static final String name = "name";
        public static final String gpin = "gpin";
    }

    /**
     * Name of the user
     */
    @Column(name = Columns.name, length = FieldConstants.NAME, nullable = false)
    private String name;

    /**
     * gpin of the user
     */
    @Embedded
    @AttributeOverride(name = Columns.gpin, column = @Column(name = UserKey.Columns.gpin, length = FieldConstants.NAME, nullable = false))
    private UserKey key;

    protected User() {// required by JPA
    }

    public User(String name, UserKey key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public UserKey getKey() {
        return key;
    }
}
