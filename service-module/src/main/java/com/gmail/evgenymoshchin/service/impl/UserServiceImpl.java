package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.UserReviewRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.RoleRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.UserReviewRepositoryImpl;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final RoleRepository roleRepository = RoleRepositoryImpl.getInstance();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final UserReviewRepository userReviewRepository = UserReviewRepositoryImpl.getInstance();
    private final ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();

    private static UserService instance;

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                Role role = roleRepository.getRoleByName(connection, userDTO.getRole());
                User user = convertDTOToObject(userDTO, role);
                userRepository.add(connection, user);
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
    public List<UserDTO> getAll() {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                List<UserDTO> usersDTO = new ArrayList<>();
                List<User> users = userRepository.getAll(connection);
                for (User user : users) {
                    usersDTO.add(convertObjectToDTO(user));
                }
                connection.commit();
                return usersDTO;
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
    public void deleteUserById(Long userId) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                userReviewRepository.deleteByUserId(connection, userId);
                userRepository.delete(connection, userId);
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
    public boolean isValidUser(String email, String password) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByEmail(connection, email);
                connection.commit();
                if (user == null) {
                    return false;
                } else {
                    return user.getPassword().equals(password);
                }
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByEmail(connection, email);
                connection.commit();
                if (user != null) {
                    return convertObjectToDTO(user);
                }
                return null;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private User convertDTOToObject(UserDTO userDTO, Role role) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPatronymic(userDTO.getPatronymic());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(role);
        return user;
    }

    private UserDTO convertObjectToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        if (user.getRole() != null) {
            userDTO.setRole(user.getRole().getName());
        }
        return userDTO;
    }
}
