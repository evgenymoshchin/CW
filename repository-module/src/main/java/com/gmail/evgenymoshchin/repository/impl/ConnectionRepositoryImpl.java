package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.constant.ConnectionConstant;
import com.gmail.evgenymoshchin.repository.exception.RepositoryException;
import com.gmail.evgenymoshchin.repository.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import static com.gmail.evgenymoshchin.repository.constant.ConnectionConstant.CACHE_PREP_STMTS;
import static com.gmail.evgenymoshchin.repository.constant.ConnectionConstant.COM_MYSQL_JDBC_DRIVER;
import static com.gmail.evgenymoshchin.repository.constant.ConnectionConstant.DATASOURCE_PASSWORD;
import static com.gmail.evgenymoshchin.repository.constant.ConnectionConstant.DATASOURCE_URL;
import static com.gmail.evgenymoshchin.repository.constant.ConnectionConstant.DATASOURCE_USERNAME;
import static com.gmail.evgenymoshchin.repository.constant.ConnectionConstant.PREP_STMT_CACHE_SIZE;
import static com.gmail.evgenymoshchin.repository.constant.ConnectionConstant.PREP_STMT_CACHE_SQL_LIMIT;

public class ConnectionRepositoryImpl implements ConnectionRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static ConnectionRepository instance;
    private static HikariDataSource hikariDataSource;
    private final PropertyUtil propertyUtil = PropertyUtil.getInstance();

    private ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            instance = new ConnectionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Connection getDataSourceConnection() {
        if (hikariDataSource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(COM_MYSQL_JDBC_DRIVER);
            hikariConfig.setJdbcUrl(propertyUtil.getProperty(DATASOURCE_URL));
            hikariConfig.setUsername(propertyUtil.getProperty(DATASOURCE_USERNAME));
            hikariConfig.setPassword(propertyUtil.getProperty(DATASOURCE_PASSWORD));
            hikariConfig.addDataSourceProperty(CACHE_PREP_STMTS, propertyUtil.getProperty(ConnectionConstant.CACHE_PREP_STMTS));
            hikariConfig.addDataSourceProperty(PREP_STMT_CACHE_SIZE, propertyUtil.getProperty(PREP_STMT_CACHE_SIZE));
            hikariConfig.addDataSourceProperty(PREP_STMT_CACHE_SQL_LIMIT, propertyUtil.getProperty(PREP_STMT_CACHE_SQL_LIMIT));
            hikariDataSource = new HikariDataSource(hikariConfig);
        }
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RepositoryException("Connection is not available", e);
        }
    }
}
