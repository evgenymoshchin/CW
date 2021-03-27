package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;

public class GetUserController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<UserDTO> users = userService.getAll();
        List<UserDTO> usersWithoutCurrent = new ArrayList<>();
        HttpSession httpSession = request.getSession(false);
        UserDTO userFromSession = (UserDTO) httpSession.getAttribute("user");
        for (UserDTO user : users) {
            if (user.getId().equals(userFromSession.getId())) {
                continue;
            } else {
                usersWithoutCurrent.add(user);
            }
        }
        usersWithoutCurrent.sort(Comparator.comparing(UserDTO::getEmail));
        request.setAttribute("users", usersWithoutCurrent);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/get_users.jsp");
        requestDispatcher.forward(request, response);
    }
}
