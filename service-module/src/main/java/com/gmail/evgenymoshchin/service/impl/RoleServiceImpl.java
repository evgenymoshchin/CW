package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.RoleRepositoryImpl;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final RoleRepository roleRepository = RoleRepositoryImpl.getInstance();
    private final ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();

    private static RoleService instance;

    private RoleServiceImpl() {
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }
        return instance;
    }

    @Override
    public void addRole(RoleEnum roleName) {
        try (Connection connection = connectionRepository.getDataSourceConnection()) {
            connection.setAutoCommit(false);
            try {
                Role role = convertDTOToObject(roleName);
                roleRepository.add(connection, role);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Role convertDTOToObject(RoleEnum roleName) {
        Role role = new Role();
        role.setName(roleName);
        return role;
    }
}
