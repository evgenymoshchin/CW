package com.gmail.evgenymoshchin.app.filters;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.impl.UserServiceImpl;
import com.gmail.evgenymoshchin.service.model.UserDTO;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gmail.evgenymoshchin.app.constant.PagesConstant.JSP_PAGES_LOCATION;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.REVIEWS_URL_VALUE;
import static com.gmail.evgenymoshchin.app.constant.PagesConstant.USERS_URL_VALUE;

public class LoginFilter implements Filter {

    private final UserService userService = UserServiceImpl.getInstance();
    private final Map<RoleEnum, String> roleMap = new HashMap<>() {{
        put(RoleEnum.ROLE_ADMINISTRATOR, USERS_URL_VALUE);
        put(RoleEnum.ROLE_USER, REVIEWS_URL_VALUE);
    }};

    private String getRedirectionUrl(RoleEnum name) {
        return roleMap.get(name);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String email = httpRequest.getParameter("email");
        String password = httpRequest.getParameter("password");
        if (email == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            boolean isValid = userService.isValidUser(email, password);
            if (isValid) {
                UserDTO userDTO = userService.findUserByEmail(email);
                HttpSession session = httpRequest.getSession();
                session.setAttribute("user", userDTO);
                String redirectionUrl = getRedirectionUrl(userDTO.getRole());
                ((HttpServletResponse) servletResponse).sendRedirect(httpRequest.getContextPath() + redirectionUrl);
            } else {
                servletRequest.getRequestDispatcher(JSP_PAGES_LOCATION + "/login_failed.jsp").forward(servletRequest, servletResponse);
            }
        }
    }
}
