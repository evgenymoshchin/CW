package com.gmail.evgenymoshchin.app.listener;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.TableService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.RoleServiceImpl;
import com.gmail.evgenymoshchin.service.impl.TableServiceImpl;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.invoke.MethodHandles;

public class InitialListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final TableService tableService = TableServiceImpl.getInstance();
    private final RoleService roleService = RoleServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("contextInitialized listener applied");

        tableService.createTablesInDataBase();
        roleService.addRole(RoleEnum.ROLE_ADMINISTRATOR);
        roleService.addRole(RoleEnum.ROLE_USER);

        UserDTO testAdmin = new UserDTO();
        testAdmin.setFirstName("evgeny");
        testAdmin.setLastName("moshchin");
        testAdmin.setPatronymic("vyacheslavovich");
        testAdmin.setPassword("1234");
        testAdmin.setEmail("ztrancer@gmail.com");
        testAdmin.setRole(RoleEnum.ROLE_ADMINISTRATOR);
        userService.addUser(testAdmin);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("contextDestroyed listener applied");
        tableService.dropTablesFromDataBase();
    }
}
