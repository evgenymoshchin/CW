package com.gmail.evgenymoshchin.repository;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<T> {

    T add(Connection connection, T object);

    List<T> getAll(Connection connection);

    void delete(Connection connection, Long id);

    void dropTableFromDataBase(Connection connection);

    void createTableInDataBase(Connection connection);
}
