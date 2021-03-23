package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.GenericRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.ADD_ROLE_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.CREATE_TABLE_ROLE_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.DROP_TABLE_ROLE_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.GET_ROLE_BY_NAME_QUERY;

public class RoleRepositoryImpl implements GenericRepository<Role> {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static GenericRepository<Role> instance;

    private RoleRepositoryImpl() {
    }

    public static GenericRepository<Role> getInstance() {
        if (instance == null) {
            instance = new RoleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Role add(Connection connection, Role role) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                ADD_ROLE_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, role.getName().name());
            int affectedRows = preparedStatement.executeUpdate();
            logger.info("added {} rows roles", affectedRows);
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedKey = generatedKeys.getLong(1);
                    role.setId(generatedKey);
                }
                return role;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't add role into database!", e);
        }
    }

    @Override
    public List<Role> getAll(Connection connection) {
        throw new UnsupportedOperationException("This method is not active");
    }

    @Override
    public void delete(Connection connection, Long id) {
        throw new UnsupportedOperationException("This method is not active");
    }

    @Override
    public void dropTableFromDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_ROLE_QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't drop role table!", e);
        }
    }

    @Override
    public void createTableInDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_ROLE_QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't create role table!", e);
        }
    }

    @Override
    public Role getRoleByName(Connection connection, RoleEnum roleName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                GET_ROLE_BY_NAME_QUERY)) {
            preparedStatement.setString(1, roleName.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getRole(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't get role from database!", e);
        }
        return null;
    }

    @Override
    public User getUserByEmail(Connection connection, String email) {
        throw new UnsupportedOperationException("This method is not active");
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        RoleEnum roleEnum = RoleEnum.valueOf(resultSet.getString("name"));
        role.setName(roleEnum);
        return role;
    }
}
