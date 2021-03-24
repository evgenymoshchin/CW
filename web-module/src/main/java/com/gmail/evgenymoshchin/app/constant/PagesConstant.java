package com.gmail.evgenymoshchin.app.constant;

public interface PagesConstant {
    String JSP_PAGES_LOCATION = "/WEB-INF/jsp";
    String USERNAME_VALIDATION_REGEX = "^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$";
    String ADMIN_ROLE_VALUE = "ADMIN";
    String USER_ROLE_VALUE = "USER";
    int COOKIES_AGE_VALUE = 10 * 20;
}
