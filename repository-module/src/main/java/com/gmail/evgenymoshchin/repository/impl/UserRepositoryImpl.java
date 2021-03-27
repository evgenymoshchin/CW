package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.List;

import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.ADD_USER_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.CREATE_USER_TABLE_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.DELETE_USER_BY_ID_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.DROP_TABLE_USER_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.GET_USERS_WITH_ROLE_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.GET_USER_BY_EMAIL_QUERY;

public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements UserRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void createTableInDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_USER_TABLE_QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't create user table!", e);
        }
    }

    @Override
    public void dropTableFromDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_USER_QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't drop user table!", e);
        }
    }

    @Override
    public User add(Connection connection, User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                ADD_USER_QUERY,
                Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPatronymic());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setLong(6, user.getRole().getId());
            int affectedRows = preparedStatement.executeUpdate();
            logger.info("added {} rows users", affectedRows);
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedKey = generatedKeys.getLong(1);
                    user.setId(generatedKey);
                }
                return user;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't add user into database!", e);
        }
    }

    @Override
    public List<User> getAll(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(GET_USERS_WITH_ROLE_QUERY)) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = getUser(resultSet);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Getting user from database failed", e);
        }
    }

    @Override
    public void delete(Connection connection, Long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            logger.info("deleted {} rows roles", affectedRows);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Deleting user from database failed", e);
        }
    }

    @Override
    public User getUserByEmail(Connection connection, String email) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                GET_USER_BY_EMAIL_QUERY)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't get user by email from database!", e);
        }
        return null;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPatronymic(resultSet.getString("patronymic"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        Role role = new Role();
        RoleEnum roleEnum = RoleEnum.valueOf(resultSet.getString("name"));
        role.setName(roleEnum);
        user.setRole(role);
        return user;
    }
}
