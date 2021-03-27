package com.gmail.evgenymoshchin.repository;

import java.sql.Connection;

public interface GenericRepository<T> {

    void createTableInDataBase(Connection connection);

    void dropTableFromDataBase(Connection connection);

    T add(Connection connection, T object);
}
