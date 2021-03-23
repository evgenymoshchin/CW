package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.GenericRepository;
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

public class UserRepositoryImpl implements GenericRepository<User> {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static GenericRepository<User> instance;

    private UserRepositoryImpl() {
    }

    public static GenericRepository<User> getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User add(Connection connection, User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO user (first_name, last_name, patronymic, password, email, role_id) VALUES (?,?,?,?,?,?)",
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
//        try (Statement statement = connection.createStatement()) {
//            try (ResultSet resultSet = statement.executeQuery(GET_USERS_DATA_WITH_ROLE_NAME_SQL)) {
//                List<User> users = new ArrayList<>();
//                while (resultSet.next()) {
//                    User user = getUser(resultSet);
//                    users.add(user);
//                }
//                return users;
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//            throw new IllegalArgumentException("Getting user from database failed", e);
//        }
        return null;
    }

    @Override
    public void delete(Connection connection, Long id) {
//        try (
//                PreparedStatement statement = connection.prepareStatement(
//                        "DELETE FROM user WHERE id=?"
//                )
//        ) {
//            statement.setInt(1, id);
//            int affectedRows = statement.executeUpdate();
//            if (affectedRows == 0) {
//                throw new SQLException("Deleting user failed, no rows affected.");
//            }
//        }
    }

    @Override
    public void dropTableFromDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS user;");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't drop user table!", e);
        }
    }

    @Override
    public void createTableInDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS user(id         INT PRIMARY KEY AUTO_INCREMENT, first_name VARCHAR(40), last_name  VARCHAR(40), patronymic VARCHAR(40), password   VARCHAR(40), email      VARCHAR(40), role_id    INT(11), FOREIGN KEY (role_id) REFERENCES role (id));");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't create user table!", e);
        }
    }

//    private User getUser(ResultSet resultSet) throws SQLException {
//        Role role = new Role();
//        role.setName((resultSet.getString("name")));
//        User user = new User();
//        user.setId(resultSet.getLong("id"));
//        user.setUserName(resultSet.getString("username"));
//        user.setPassword(resultSet.getString("password"));
//        user.setCreatedBy(resultSet.getString("createdBy"));
//        user.setRole(role);
//        return user;
//    }
}
