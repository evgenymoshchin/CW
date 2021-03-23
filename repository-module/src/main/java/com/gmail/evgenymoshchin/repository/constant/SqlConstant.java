package com.gmail.evgenymoshchin.repository.constant;

public interface SqlConstant {
    String DROP_TABLE_USER_QUERY = "DROP TABLE IF EXISTS user;";
    String DROP_TABLE_ROLE_QUERY = "DROP TABLE IF EXISTS role;";
    String CREATE_TABLE_ROLE_QUERY = "CREATE TABLE IF NOT EXISTS role (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20) NOT NULL);";
    String CREATE_USER_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS user(id INT PRIMARY KEY AUTO_INCREMENT, first_name VARCHAR(40), last_name  VARCHAR(40), patronymic VARCHAR(40), password VARCHAR(40), email VARCHAR(40), role_id INT(11), FOREIGN KEY (role_id) REFERENCES role (id));";
    String ADD_ROLE_QUERY = "INSERT INTO role(name)VALUES (?);";
    String ADD_USER_QUERY = "INSERT INTO user (first_name, last_name, patronymic, password, email, role_id) VALUES (?,?,?,?,?,?)";
    String GET_USERS_WITH_ROLE_QUERY = "SELECT user.id as id, first_name, last_name, patronymic, password, email, role_id, name FROM user JOIN role r on user.role_id = r.id;";
    String GET_ROLE_BY_NAME_QUERY = "SELECT id, name FROM role WHERE name = ?;";
    String DELETE_USER_BY_ID_QUERY = "DELETE FROM user WHERE id = ?";

}
