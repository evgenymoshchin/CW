package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.UserReview;

import java.sql.Connection;
import java.util.List;

public interface UserReviewRepository extends GenericRepository<UserReview> {

    List<UserReview> getAll(Connection connection);

    void delete(Connection connection, Long id);

    void deleteByUserId(Connection connection, Long userId);
}
