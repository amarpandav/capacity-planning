package com.ubs.cpt.infra.spring.datasource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Amar Pandav
 */
public enum Datasource {

    //sql.syntax_ora=true -> for using dual table and other oracle specific things
    HSQLDB(hsqldbDatasource(Constants.HSQLDB_URL + ";hsqldb.tx=mvcc;hsqldb.tx_level=read_commited;sql.syntax_mys=true", "sa", ""), hsqldbProperties()),

    //TODO DEV MYSQL
    MYSQL_DEV(mySQLDatasource("jdbc:mysql://localhost:3306/smoothieApp?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8_general_ci&characterSetResults=utf8", "root", "root"), mySQLProperties()),

    //TODO PROD
    MYSQL_PROD(mySQLDatasource("jdbc:mysql://127.8.205.2:3306/smoothieApp?autoReconnect=true&amp;characterEncoding=utf8", "adminqje9rBK", "C5eST74sKkN9"), mySQLProperties());

    private DataSource dataSource;

    private Properties properties;

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
        dataSource.setMaxActive(20);
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


    private static DataSource mySQLDatasource(String url, String username, String password) {
        BasicDataSource dataSource = null;
        System.out.println("Inside DataSource - url:" + url + ", user:" + username + ", password:" + password);
        if (!StringUtils.isEmpty(url) && !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            dataSource.setInitialSize(2);
            dataSource.setMaxActive(30);
            dataSource.setMaxIdle(10);
        }
        return dataSource;
    }

    private static Properties mySQLProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.format_sql", "false");
        properties.put("hibernate.use_sql_comments", "true");
        properties.put("hibernate.search.default.directory_provider", "org.hibernate.search.store.RAMDirectoryProvider");
        properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.archive.autodetection", "none");
        properties.put("hibernate.jdbc.fetch_size", "100");  //TODO: which settings?
        properties.put("hibernate.jdbc.batch_size", "30");   //TODO: which settings?
        /*properties.put("hibernate.useUnicode", "true");
        properties.put("hibernate.charSet","utf8");
        properties.put("hibernate.characterEncoding", "utf8");
        properties.put("hibernate.connectionCollation", "utf8_general_ci");
        properties.put("hibernate.characterSetResults", "utf8");*/
        return properties;
    }

    public static class Constants {
        public static final String HSQLDB_URL = "jdbc:hsqldb:mem:myApp";
    };

}
