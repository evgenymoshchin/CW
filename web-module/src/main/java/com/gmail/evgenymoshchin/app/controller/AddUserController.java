package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.mysql.cj.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.EMAIL_VALIDATION_REGEX;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.LATIN_VALIDATION_REGEX;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.USERS_URL_VALUE;

public class AddUserController extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/add_user.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDTO userDTO = getUserDTO(request);
        if (isUserValid(userDTO)) {
            userService.addUser(userDTO);
            response.sendRedirect(request.getContextPath() + USERS_URL_VALUE);
        } else {
            request.getRequestDispatcher(JSP_PAGES_LOCATION + "/login_failed.jsp").forward(request,response);
        }
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

    private boolean isUserValid(UserDTO userDTO) {
        return isValidEmail(userDTO.getEmail()) && isValidString(userDTO.getFirstName()) && isValidString(userDTO.getLastName()) && isValidString(userDTO.getPatronymic());
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private boolean isValidString(String someParameter) {
        Pattern pattern = Pattern.compile(LATIN_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(someParameter);
        return !StringUtils.isNullOrEmpty(someParameter) && matcher.find();
    }
}
