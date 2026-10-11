package com.amarpandav.cpt.domain.entity.user;

import com.amarpandav.cpt.domain.base.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


/**
 * Human readable key instead of uuid i.e. employee ID
 *
 * @author Amar Pandav
 */
@Embeddable
public class UserKey implements Comparable<UserKey>, Serializable {

    public static final class Columns {
        public static final String EMPLOYEE_ID = "employee_id"; //Auto
    }


    /**
     * id of the user
     */
    @Column(name = Columns.EMPLOYEE_ID, length = FieldConstants.UUID_LEN, nullable = false)
    private String employeeId;

    public UserKey(String employeeId) {
        if (employeeId == null) {
            throw new NullPointerException("key can't be null.");
        }
        this.employeeId = employeeId;
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
        return employeeId.equals(that.employeeId);
    }

    @Override
    public int hashCode() {
        return employeeId.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserKey");
        sb.append("{employeeId='").append(employeeId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public int compareTo(UserKey o) {
        return employeeId.compareTo(o.employeeId);
    }
}
