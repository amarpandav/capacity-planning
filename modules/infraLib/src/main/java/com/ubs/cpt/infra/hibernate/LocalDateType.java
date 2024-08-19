package com.ubs.cpt.infra.hibernate;

import com.ubs.cpt.infra.datetime.CoreDateTimeUtils;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.type.ImmutableType;
import org.joda.time.LocalDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static com.ubs.cpt.infra.datetime.CoreDateTimeUtils.toDate;


/**
 * @author Amar Pandav
 */
public class LocalDateType extends ImmutableType {
    //private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(LocalDateType.class);

    public Object get(ResultSet rs, String name) throws HibernateException, SQLException {

        return CoreDateTimeUtils.localDateJoda(rs.getDate(name));
    }

    public void set(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        st.setDate(index, new java.sql.Date(toDate((LocalDate) value).getTime()));
    }

    public int sqlType() {
        return Types.DATE;
    }

    public String toString(Object value) throws HibernateException {
        return value.toString();
    }

    public Class getReturnedClass() {
        return LocalDate.class;
    }

    public boolean isEqual(Object x, Object y) {
        return x == y || (x != null && y != null && toDate((LocalDate) x).compareTo(toDate((LocalDate) y)) == 0);
    }

    public int getHashCode(Object x, EntityMode entityMode) {
        return x.hashCode();
    }

    public String getName() {
        return "joda_local_date";
    }

    public Object fromStringValue(String xml) {
        return CoreDateTimeUtils.localDateJoda(xml);
    }


}
