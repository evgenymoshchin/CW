package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
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

public class DeleteUserController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] selectedIds = request.getParameterValues("userId");
        Long id;
        for (String idFromForm : selectedIds) {
            System.out.println(idFromForm);
            try {
                id = Long.parseLong(idFromForm);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
                throw new NumberFormatException("Id should be a number value");
            }
            userService.deleteUserById(id);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/success_operation.jsp");
        requestDispatcher.forward(request, response);
    }
}
