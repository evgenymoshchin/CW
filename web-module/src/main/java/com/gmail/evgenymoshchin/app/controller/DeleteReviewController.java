package com.gmail.evgenymoshchin.app.controller;

import com.gmail.evgenymoshchin.service.UserReviewService;
import com.gmail.evgenymoshchin.service.impl.UserReviewServiceImpl;
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

public class DeleteReviewController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserReviewService userReviewService = UserReviewServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] reviews = request.getParameterValues("reviewId");
        Long id;
        for (String idFromForm : reviews) {
            System.out.println(idFromForm);
            try {
                id = Long.parseLong(idFromForm);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
                throw new NumberFormatException("Id should be a number value");
            }
            userReviewService.deleteUserReviewById(id);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSP_PAGES_LOCATION + "/success_operation.jsp");
        requestDispatcher.forward(request, response);
    }
}