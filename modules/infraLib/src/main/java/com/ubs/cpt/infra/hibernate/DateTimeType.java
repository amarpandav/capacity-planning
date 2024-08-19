package com.ubs.cpt.infra.hibernate;

import com.ubs.cpt.infra.datetime.CoreDateTimeUtils;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.type.ImmutableType;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.Date;

/**
 * @author Amar Pandav
 */
public class DateTimeType extends ImmutableType {
    //private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(LocalDateType.class);

    public Object get(ResultSet rs, String name) throws HibernateException, SQLException {
        Timestamp ts = rs.getTimestamp(name);
        return CoreDateTimeUtils.dateTimeJoda(ts);
    }

    public void set(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        long millis = ((DateTime) value).getMillis();
        st.setTimestamp(index, CoreDateTimeUtils.javaSqlTimestamp(millis));
    }

    public int sqlType() {
        return Types.TIMESTAMP;
    }

    public String toString(Object value) throws HibernateException {
        return value.toString();
    }

    public Class getReturnedClass() {
        return DateTime.class;
    }

    public boolean isEqual(Object x, Object y) {
        return x == y || (x != null && y != null && toDate(x).compareTo(toDate(y)) == 0);
    }

    private Date toDate(Object dateTime) {
        return CoreDateTimeUtils.toDate(((DateTime) dateTime));
    }

    public int getHashCode(Object x, EntityMode entityMode) {
        return x.hashCode();
    }

    public String getName() {
        return "joda_date_time";
    }

    public Object fromStringValue(String xml) {
        return CoreDateTimeUtils.dateTimeJoda(xml);
    }

}