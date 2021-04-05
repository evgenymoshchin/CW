package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.UserReviewRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.RoleRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserReviewRepositoryImpl;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
import com.gmail.evgenymoshchin.service.TableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class TableServiceImpl implements TableService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final RoleRepository roleRepository = RoleRepositoryImpl.getInstance();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final UserReviewRepository userReviewRepository = UserReviewRepositoryImpl.getInstance();
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
                userReviewRepository.dropTableFromDataBase(connection);
                userRepository.dropTableFromDataBase(connection);
                roleRepository.dropTableFromDataBase(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Can't drop tables from database!", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Can't drop tables from database!", e);
        }
    }

    @Override
    public void createTablesInDataBase() {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                roleRepository.createTableInDataBase(connection);
                userRepository.createTableInDataBase(connection);
                userReviewRepository.createTableInDataBase(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Can't create tables in database!", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Can't create tables in database!", e);
        }
    }
}
