package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<T> {

    T add(Connection connection, T object);

    List<T> getAll(Connection connection);

    void delete(Connection connection, Long id);

    void deleteByUserId(Connection connection, Long userId);

    void dropTableFromDataBase(Connection connection);

    void createTableInDataBase(Connection connection);

    Role getRoleByName(Connection connection, RoleEnum roleName);

    User getUserByEmail(Connection connection, String email);
}
