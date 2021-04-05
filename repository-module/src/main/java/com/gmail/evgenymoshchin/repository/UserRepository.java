package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository<User> {

    List<User> getAll(Connection connection);

    void delete(Connection connection, Long id);

    User getUserByEmail(Connection connection, String email);
}
