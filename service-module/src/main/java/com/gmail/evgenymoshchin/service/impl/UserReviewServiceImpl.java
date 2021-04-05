package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.UserReviewRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserReviewRepositoryImpl;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.repository.model.UserReview;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
import com.gmail.evgenymoshchin.service.UserReviewService;
import com.gmail.evgenymoshchin.service.model.UserReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserReviewServiceImpl implements UserReviewService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final UserReviewRepository userReviewRepository = UserReviewRepositoryImpl.getInstance();

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
                throw new ServiceException("Can't save review from database!", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Can't save review from database!", e);
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
                throw new ServiceException("Can't get review from database!", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Can't get review from database!", e);
        }
    }

    @Override
    public void deleteUserReviewById(Long id) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                userReviewRepository.delete(connection, id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Can't delete review from database!", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Can't delete review from database!", e);
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
        userReviewDTO.setId(userReview.getId());
        userReviewDTO.setTopic(userReview.getTopic());
        userReviewDTO.setReview(userReview.getReview());
        userReviewDTO.setDate(userReview.getDate());
        if (userReview.getUser() != null) {
            userReviewDTO.setUserDTO(userReviewDTO.getUserDTO());
        }
        return userReviewDTO;
    }
}
