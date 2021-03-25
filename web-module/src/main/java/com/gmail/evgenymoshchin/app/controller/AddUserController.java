package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;

public class AddUserController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/add_user.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userDTO = getUserDTO(request);
        userService.addUser(userDTO);
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private UserDTO getUserDTO(HttpServletRequest request) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(request.getParameter("firstName"));
        userDTO.setLastName(request.getParameter("lastName"));
        userDTO.setPatronymic(request.getParameter("patronymic"));
        userDTO.setEmail(request.getParameter("email"));
        userDTO.setPassword(request.getParameter("password"));
        String role = request.getParameter("role");
        RoleEnum roleEnum = RoleEnum.valueOf(role);
        userDTO.setRole(roleEnum);
        return userDTO;
    }
}
