package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ConnectionRepository;
import com.gmail.evgenymoshchin.repository.GenericRepository;
import com.gmail.evgenymoshchin.repository.impl.ConnectionRepositoryImpl;
import com.gmail.evgenymoshchin.repository.impl.RoleRepositoryImpl;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.model.RoleDTO;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final GenericRepository<Role> roleRepository = RoleRepositoryImpl.getInstance();
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

    @Override
    public List<RoleDTO> getRoles() {
//        try (Connection connection = connectionRepository.getDataSourceConnection()) {
//            connection.setAutoCommit(false);
//            try {
//                List<RoleDTO> rolesDTO = new ArrayList<>();
//                List<Role> roles = roleRepository.getAll(connection);
//                for (Role role : roles) {
//                    rolesDTO.add(convertObjectToDTO(role));
//                }
//                connection.commit();
//                return rolesDTO;
//            } catch (SQLException e) {
//                connection.rollback();
//                logger.error(e.getMessage(), e);
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//        }
        return Collections.emptyList();
    }

    private Role convertDTOToObject(RoleEnum roleName) {
        Role role = new Role();
        role.setName(roleName);
        return role;
    }

//    private RoleDTO convertObjectToDTO(Role role) {
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setName(role.getName());
//        return roleDTO;
//    }
}
