package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.GenericRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserReviewRepositoryImpl;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.repository.model.UserReview;
import com.gmail.evgenymoshchin.service.UserReviewService;
import com.gmail.evgenymoshchin.service.model.UserReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserReviewServiceImpl implements UserReviewService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private final GenericRepository<User> userRepository = UserRepositoryImpl.getInstance();
    private final GenericRepository<UserReview> userReviewRepository = UserReviewRepositoryImpl.getInstance();

    private static UserReviewService instance;

    private UserReviewServiceImpl() {
    }

    public static UserReviewService getInstance() {
        if (instance == null) {
            instance = new UserReviewServiceImpl();
        }
        return instance;
    }

    @Override
    public void addUserReview(UserReviewDTO userReviewDTO) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByEmail(connection, userReviewDTO.getUserDTO().getEmail());
                UserReview userReview = convertDTOToObject(userReviewDTO, user);
                userReviewRepository.add(connection, userReview);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<UserReviewDTO> getAll() {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                List<UserReviewDTO> userReviewDTOS = new ArrayList<>();
                List<UserReview> userReviews = userReviewRepository.getAll(connection);
                for (UserReview userReview : userReviews) {
                    userReviewDTOS.add(convertObjectToDTO(userReview));
                }
                connection.commit();
                return userReviewDTOS;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteUserReviewById(Long userId) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                userReviewRepository.delete(connection, userId);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private UserReview convertDTOToObject(UserReviewDTO userReviewDTO, User user) {
        UserReview userReview = new UserReview();
        userReview.setTopic(userReviewDTO.getTopic());
        userReview.setReview(userReviewDTO.getReview());
        userReview.setDate(userReviewDTO.getDate());
        userReview.setUser(user);
        return userReview;
    }

    private UserReviewDTO convertObjectToDTO(UserReview userReview) {
        UserReviewDTO userReviewDTO = new UserReviewDTO();
        userReviewDTO.setTopic(userReview.getTopic());
        userReviewDTO.setReview(userReview.getReview());
        userReviewDTO.setDate(userReview.getDate());
        if (userReview.getUser() != null) {
            userReviewDTO.setUserDTO(userReviewDTO.getUserDTO());
        }
        return userReviewDTO;
    }
}