package com.gmail.evgenymoshchin;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.TableService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.RoleServiceImpl;
import com.gmail.evgenymoshchin.service.impl.TableServiceImpl;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;

public class App {
    private static final TableService tableService = TableServiceImpl.getInstance();
    private static final RoleService roleService = RoleServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();

    public static void main(String[] args) {
        tableService.dropTablesFromDataBase();
        tableService.createTablesInDataBase();

        roleService.addRole(RoleEnum.ROLE_ADMINISTRATOR);
        roleService.addRole(RoleEnum.ROLE_USER);

        UserDTO first = new UserDTO();
        first.setFirstName("evgeny");
        first.setLastName("moshchin");
        first.setPatronymic("vyacheslavovich");
        first.setPassword("1234");
        first.setEmail("nixon_dn@gmail.com");
        first.setRole(RoleEnum.ROLE_ADMINISTRATOR);

        UserDTO second = new UserDTO();
        second.setFirstName("evg");
        second.setLastName("moshin");
        second.setPatronymic("cheslavovich");
        second.setPassword("123");
        second.setEmail("nixon@gmail.com");
        second.setRole(RoleEnum.ROLE_USER);

        userService.addUser(first);
        userService.addUser(second);

        userService.getAll();
        System.out.println(userService.isValidUser("nixon_dn@gmail.com", "1234"));
        userService.deleteUserById(2L);
    }
}
