package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.UserReviewRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.repository.model.UserReview;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.ADD_REVIEW_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.CREATE_TABLE_REVIEW_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.DELETE_REVIEW_BY_ID_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.DELETE_REVIEW_BY_USER_ID;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.DROP_TABLE_REVIEW_QUERY;
import static com.gmail.evgenymoshchin.repository.constant.SqlConstant.GET_REVIEW_WITH_USER_ID_QUERY;

public class UserReviewRepositoryImpl extends GenericRepositoryImpl<UserReview> implements UserReviewRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public static UserReviewRepository instance;

    private UserReviewRepositoryImpl() {
    }

    public static UserReviewRepository getInstance() {
        if (instance == null) {
            instance = new UserReviewRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void createTableInDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_REVIEW_QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't create userReview table!", e);
        }
    }

    @Override
    public void dropTableFromDataBase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_REVIEW_QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't drop userReview table!", e);
        }
    }

    @Override
    public UserReview add(Connection connection, UserReview userReview) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                ADD_REVIEW_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, userReview.getTopic());
            preparedStatement.setString(2, userReview.getReview());
            preparedStatement.setLong(3, userReview.getUser().getId());
            int affectedRows = preparedStatement.executeUpdate();
            logger.info("added {} rows userReviews", affectedRows);
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedKey = generatedKeys.getLong(1);
                    userReview.setId(generatedKey);
                }
                return userReview;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can't add userReview into database!", e);
        }
    }

    @Override
    public List<UserReview> getAll(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(GET_REVIEW_WITH_USER_ID_QUERY)) {
                List<UserReview> userReviews = new ArrayList<>();
                while (resultSet.next()) {
                    UserReview userReview = getUserReview(resultSet);
                    userReviews.add(userReview);
                }
                return userReviews;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Getting userReview from database failed", e);
        }
    }

    @Override
    public void delete(Connection connection, Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REVIEW_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            logger.info("deleted {} rows userReviews", affectedRows);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Deleting userReview from database failed", e);
        }
    }

    @Override
    public void deleteByUserId(Connection connection, Long userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REVIEW_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            int affectedRows = preparedStatement.executeUpdate();
            logger.info("deleted {} rows userReviews", affectedRows);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Deleting userReview from database failed", e);
        }
    }

    private UserReview getUserReview(ResultSet resultSet) throws SQLException {
        UserReview userReview = new UserReview();
        userReview.setId(resultSet.getLong("id"));
        userReview.setTopic(resultSet.getString("topic"));
        userReview.setReview(resultSet.getString("review"));
        userReview.setDate(resultSet.getString("date"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        userReview.setUser(user);
        return userReview;
    }
}
