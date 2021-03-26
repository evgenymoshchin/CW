package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.UserReviewService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.UserReviewServiceImpl;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;

public class AddReviewController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserReviewService userReviewService = UserReviewServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AddReviewController");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/add_review.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserReviewDTO userReviewDTO = getUserReviewDTO(request);
        userReviewService.addUserReview(userReviewDTO);
        response.sendRedirect(request.getContextPath() + "/reviews");
    }

    private UserReviewDTO getUserReviewDTO(HttpServletRequest request) {
        UserReviewDTO userReviewDTO = new UserReviewDTO();
        userReviewDTO.setTopic(request.getParameter("topic"));
        userReviewDTO.setReview(request.getParameter("review"));
        userReviewDTO.setDate(request.getParameter("date"));
//        HttpSession httpSession = request.getSession();
        HttpSession httpSession = request.getSession(false);
        UserDTO user = (UserDTO)httpSession.getAttribute("user");
        logger.info(user.getId());
//        String user = (String) httpSession.getAttribute("user");
        userReviewDTO.setUserDTO(user);
//        userReviewDTO.setId(user.getId());
//        userReviewDTO.setUser(new User(httpSession.getAttribute("user")));
        return userReviewDTO;
    }
}
