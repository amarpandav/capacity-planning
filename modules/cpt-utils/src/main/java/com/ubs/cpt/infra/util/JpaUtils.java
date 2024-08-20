package com.ubs.cpt.infra.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Amar Pandav
 */
public class JpaUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JpaUtils.class);

    public static String escapeLikeParameter(String parameter) {
        parameter = parameter.replaceAll("\\\\", "\\\\\\\\");  // "\" --> "\\"
        parameter = parameter.replaceAll("%", "\\\\%");        // "%" --> "\%"
        parameter = parameter.replaceAll("_", "\\\\_");        // "_" --> "\_"
        return parameter;
    }

    /**
     * Convenient util to get a single result from a query in an "empty result"-friendly way.
     * Instead of using {@link jakarta.persistence.Query#getSingleResult()} (which throws a NoResultException) you can
     * use {@link jakarta.persistence.Query#getResultList()} and convert it to a single result with this static method.
     *
     * @param resultList a JPA query result list with zero or one element
     * @param <E>        Type of queried object
     * @return null if result list was empty, or first element of list if size of list is one
     * @throws jakarta.persistence.NonUniqueResultException if given list contains mor than one elements
     */
    @SuppressWarnings("unchecked")
    public static <E> E toSingleResult(List resultList) {
        if (resultList.isEmpty()) {
            return null;
        } else if (resultList.size() == 1) {
            return (E) resultList.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> E toSingleResult(Query query) {
        return (E) toSingleResult(query.getResultList());
    }

    public static void setReadOnlyHint(Query query) {
        query.setHint("org.hibernate.readOnly", "true");
    }

    public static void setFetchSize(Query query, int fetchSize) {
        query.setHint("org.hibernate.fetchSize", fetchSize);
    }

    public static void setFetchSizeLargeResultSet(Query query) {
        setFetchSize(query, 10000);
    }

    public static Connection getConnection(EntityManager em) {
        SessionImplementor sessionImplementor = em.unwrap(SessionImplementor.class);
        try {
            return sessionImplementor.getJdbcConnectionAccess().obtainConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "error reading connection info, improve this highly specific implementation for your environment", e);
        }
    }

    public static PreparedStatement createPreparedStatement(EntityManager em, String sql) {
        try {
            return getConnection(em)
                    .prepareStatement(sql);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "error reading connection info for prepareStatement, improve this highly specific implementation for your environment", e);
        }
    }

    /**
     * <p>Determines whether or not the given connection refers to a HSQLDB database.</p>
     * This is implemented on a very provider specific way, so DON'T use in production.
     *
     * @param connection the connection you want to inspect
     * @return <code>true</code>, if the connection refers to a HSQLDB database; <code>false</code> otherwise
     */
    public static boolean isHsqlConnection(Connection connection) {
        try {
            String driverName = connection.getMetaData().getDriverName();
            return driverName != null && driverName.contains("HSQL");
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * <p>Determines whether or not the given entity manager connection refers to a HSQLDB database.</p>
     * This is implemented on a very provider specific way, so DON'T use in production.
     *
     * @param em the entityManager you want to inspect
     * @return <code>true</code>, if the connection refers to a HSQLDB database; <code>false</code> otherwise
     */
    public static boolean isHsqlConnection(EntityManager em) {
        return isHsqlConnection(JpaUtils.getConnection(em));
    }

    /**
     * <p>Determines whether or not the given entity manager factory refers to a HSQLDB database.</p>
     * This is implemented on a very provider specific way, so DON'T use in production.
     *
     * @param emf the entityManagerFactory you want to inspect
     * @return <code>true</code>, if the connection refers to a HSQLDB database; <code>false</code> otherwise
     */
    public static boolean isHsqlConnection(EntityManagerFactory emf) {
        try {
            return ((String) emf.getProperties().get("hibernate.dialect")).contains("HSQL");
        } catch (Exception e) {
            throw new IllegalStateException(
                    "error reading connection info, improve this highly specific implementation for your environment", e);
        }
    }

    public static enum QueryType {
        JPA, NATIVE, UNKNOWN;
    }


}
