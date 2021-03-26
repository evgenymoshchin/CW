package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.service.UserReviewService;
import com.gmail.evgenymoshchin.service.impl.UserReviewServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.List;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;

public class GetReviewController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserReviewService userReviewService = UserReviewServiceImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GetReviewController");
        List<UserReviewDTO> reviews = userReviewService.getAll();
        request.setAttribute("reviews", reviews);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/get_review.jsp");
        requestDispatcher.forward(request, response);
    }
}
