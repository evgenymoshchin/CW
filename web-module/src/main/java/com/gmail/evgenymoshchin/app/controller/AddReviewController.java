package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.service.UserReviewService;
import com.gmail.evgenymoshchin.service.impl.UserReviewServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserReviewDTO;
import com.mysql.cj.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.REVIEWS_URL_VALUE;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.REVIEW_VALIDATION_REGEX;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.TOPIC_VALIDATION_REGEX;

public class AddReviewController extends HttpServlet {

    private final UserReviewService userReviewService = UserReviewServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/add_review.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserReviewDTO userReviewDTO = getUserReviewDTO(request);
        if (isReviewValid(userReviewDTO)) {
            userReviewService.addUserReview(userReviewDTO);
            response.sendRedirect(request.getContextPath() + REVIEWS_URL_VALUE);
        } else {
            request.getRequestDispatcher(JSP_PAGES_LOCATION + "/login_failed.jsp").forward(request, response);
        }
    }

    private UserReviewDTO getUserReviewDTO(HttpServletRequest request) {
        UserReviewDTO userReviewDTO = new UserReviewDTO();
        userReviewDTO.setTopic(request.getParameter("topic"));
        userReviewDTO.setReview(request.getParameter("review"));
        userReviewDTO.setDate(request.getParameter("date"));
        HttpSession httpSession = request.getSession(false);
        UserDTO user = (UserDTO) httpSession.getAttribute("user");
        userReviewDTO.setUserDTO(user);
        return userReviewDTO;
    }

    private boolean isReviewValid(UserReviewDTO userReviewDTO) {
        return isValidTopic(userReviewDTO.getTopic()) && isValidReview(userReviewDTO.getReview());
    }

    private boolean isValidReview(String review) {
        Pattern pattern = Pattern.compile(REVIEW_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(review);
        return !StringUtils.isNullOrEmpty(review) && matcher.find();
    }

    private boolean isValidTopic(String review) {
        Pattern pattern = Pattern.compile(TOPIC_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(review);
        return !StringUtils.isNullOrEmpty(review) && matcher.find();
    }
}
