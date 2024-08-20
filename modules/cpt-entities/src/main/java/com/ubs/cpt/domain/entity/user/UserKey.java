package com.ubs.cpt.domain.entity.user;

import com.ubs.cpt.domain.base.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


/**
 * Human readable key instead of uuid i.e. GPIN
 *
 * @author Amar Pandav
 */
@Embeddable
public class UserKey implements Comparable<UserKey>, Serializable {

    public static final class Columns {
        public static final String gpin = "gpin"; //Auto
    }


    /**
     * id of the user
     */
    @Column(name = Columns.gpin, length = FieldConstants.UUID_LEN, nullable = false)
    private String gpin;

    public UserKey(String gpin) {
        if (gpin == null) {
            throw new NullPointerException("key can't be null.");
        }
        this.gpin = gpin;
    }

    protected UserKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserKey)) {
            return false;
        }
        UserKey that = (UserKey) o;
        return gpin.equals(that.gpin);
    }

    @Override
    public int hashCode() {
        return gpin.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserKey");
        sb.append("{gpin='").append(gpin).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getGpin() {
        return gpin;
    }

    @Override
    public int compareTo(UserKey o) {
        return gpin.compareTo(o.gpin);
    }
}
