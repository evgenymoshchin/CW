package com.gmail.evgenymoshchin.repository;

import java.sql.Connection;

public interface ConnectionRepository {

    Connection getDataSourceConnection();
}
