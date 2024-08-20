package com.ubs.cpt.web.config.datasource;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Amar Pandav
 */
public enum Datasource {

    //sql.syntax_ora=true -> for using dual table and other oracle specific things
    HSQLDB(hsqldbDatasource(Constants.HSQLDB_URL + ";hsqldb.tx=mvcc;hsqldb.tx_level=read_commited;sql.syntax_mys=true", "sa", ""), hsqldbProperties());

    private final DataSource dataSource;

    private final Properties properties;

    // ------------------------------------------ Constructors

    Datasource(DataSource dataSource, Properties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    // ------------------------------------------ Public methods

    public DataSource getDataSource() {
        return dataSource;
    }

    public Properties getProperties() {
        return properties;
    }

    // ------------------------------------------ Utility methods

    private static DataSource hsqldbDatasource(String url, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setInitialSize(1);
        dataSource.setMaxIdle(10);

        return dataSource;
    }


    private static Properties hsqldbProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.format_sql", "false");
        properties.put("hibernate.use_sql_comments", "true");
        properties.put("hibernate.search.default.directory_provider", "org.hibernate.search.store.RAMDirectoryProvider");
        properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.archive.autodetection", "none");

        return properties;
    }


    public static class Constants {
        public static final String HSQLDB_URL = "jdbc:hsqldb:mem:myApp";
    }

    ;

}
