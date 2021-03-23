package com.gmail.evgenymoshchin;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.GenericRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.RoleRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserRepositoryImpl;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class App {

    private static final GenericRepository<Role> roleGenericRepository = RoleRepositoryImpl.getInstance();
    private static final GenericRepository<User> userGenericRepository = UserRepositoryImpl.getInstance();
    private static final ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();

    public static void main(String[] args) {

        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                roleGenericRepository.createTableInDataBase(connection);
                userGenericRepository.createTableInDataBase(connection);
                Role role = new Role();
                role.setName(RoleEnum.valueOf("ROLE_ADMINISTRATOR"));
                roleGenericRepository.add(connection, role);
                User user = new User();
                user.setFirstName("aaaa");
                user.setLastName("bbbbb");
                user.setPatronymic("vcccccc");
                user.setEmail("gom.com");
                user.setPassword("1111");
                user.setRole(role);
                userGenericRepository.add(connection, user);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
        }
    }


}
