package com.gmail.evgenymoshchin.app.filters;

import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;
import static com.gmail.evgenymoshchin.app.filters.AccessFilter.getRedirectionUrl;

public class LoginFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("LoginFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String email = httpRequest.getParameter("email");
        String password = httpRequest.getParameter("password");
        if (email == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            boolean isValid = userService.isValidUser(email, password);
            if (isValid) {
                UserDTO user = userService.findUserByEmail(email);
                HttpSession session = httpRequest.getSession();
                session.setAttribute("user", user);
                String redirectionUrl = getRedirectionUrl(user.getRole());
                ((HttpServletResponse) servletResponse).sendRedirect(httpRequest.getContextPath() + redirectionUrl);
            } else {
                ((HttpServletResponse) servletResponse).sendRedirect(JSP_PAGES_LOCATION + "/login_failed.jsp");
            }
        }
    }
}
