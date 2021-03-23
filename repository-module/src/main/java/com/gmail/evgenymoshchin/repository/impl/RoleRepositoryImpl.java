package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.GenericRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
                "INSERT INTO role(name)VALUES (?);", Statement.RETURN_GENERATED_KEYS
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

    }

    @Override
    public void dropTableFromDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS role;");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't drop role table!", e);
        }
    }

    @Override
    public void createTableInDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS role ( id   INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20) NOT NULL);"
            );
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't create role table!", e);
        }
    }

    private Role getRole(ResultSet rs) throws SQLException {
//        Role role = new Role();
//        role.setId((long) rs.getInt("r.id"));
//        RoleEnum roleEnum = RoleEnum.valueOf(rs.getString("r.name"));
//        role.setName(roleEnum);
//        role.setDescription(rs.getString("r.description"));
//        return role;
        return null;
    }
}
