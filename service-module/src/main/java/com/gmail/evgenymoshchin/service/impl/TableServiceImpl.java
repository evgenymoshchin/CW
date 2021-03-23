package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.GenericRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.RoleRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserRepositoryImpl;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.TableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class TableServiceImpl implements TableService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final GenericRepository<Role> roleRepository = RoleRepositoryImpl.getInstance();
    private static final GenericRepository<User> userRepository = UserRepositoryImpl.getInstance();
    private final ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();

    private static TableService instance;

    private TableServiceImpl() {
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new TableServiceImpl();
        }
        return instance;
    }

    @Override
    public void dropTablesFromDataBase() {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                userRepository.dropTableFromDataBase(connection);
                roleRepository.dropTableFromDataBase(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void createTablesInDataBase() {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                roleRepository.createTableInDataBase(connection);
                userRepository.createTableInDataBase(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
