package com.gmail.evgenymoshchin.repository.constant;

public interface SqlConstant {
    String DROP_TABLE_USER_QUERY = "DROP TABLE IF EXISTS user;";
    String DROP_TABLE_ROLE_QUERY = "DROP TABLE IF EXISTS role;";
    String DROP_TABLE_REVIEW_QUERY = "DROP TABLE IF EXISTS reviews;";
    String CREATE_TABLE_ROLE_QUERY = "CREATE TABLE IF NOT EXISTS role (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20) NOT NULL);";
    String CREATE_USER_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, first_name VARCHAR(40), last_name  VARCHAR(40), patronymic VARCHAR(40), password VARCHAR(40), email VARCHAR(40), role_id BIGINT, FOREIGN KEY (role_id) REFERENCES role (id));";
    String CREATE_TABLE_REVIEW_QUERY = "CREATE TABLE IF NOT EXISTS reviews (id BIGINT PRIMARY KEY AUTO_INCREMENT, topic VARCHAR(30), review VARCHAR(180), date DATETIME, user_id BIGINT, FOREIGN KEY (user_id)REFERENCES user (id));";
    String ADD_ROLE_QUERY = "INSERT INTO role(name) VALUES (?);";
    String ADD_USER_QUERY = "INSERT INTO user (first_name, last_name, patronymic, password, email, role_id) VALUES (?,?,?,?,?,?)";
    String ADD_REVIEW_QUERY = "INSERT INTO reviews (topic, review, date, user_id) VALUES (?,?,now(),?)";
    String GET_USERS_WITH_ROLE_QUERY = "SELECT user.id as id, first_name, last_name, patronymic, password, email, role_id, name FROM user JOIN role r on user.role_id = r.id;";
    String GET_USER_BY_EMAIL_QUERY = "SELECT user.id as id,first_name, last_name, patronymic, password, email, role_id, name FROM user LEFT JOIN role r on user.role_id = r.id WHERE email = ?;";
    String GET_REVIEW_WITH_USER_ID_QUERY = "SELECT reviews.id as id, topic,review,date,user_id FROM reviews JOIN user u on reviews.user_id = u.id;";
    String GET_ROLE_BY_NAME_QUERY = "SELECT id, name FROM role WHERE name = ?;";
    String DELETE_USER_BY_ID_QUERY = "DELETE FROM user WHERE id = ?";
    String DELETE_REVIEW_BY_ID_QUERY = "DELETE FROM reviews WHERE id = ?;";
    String DELETE_REVIEW_BY_USER_ID = "DELETE FROM reviews WHERE user_id = ?;";
}
