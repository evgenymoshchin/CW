package com.gmail.evgenymoshchin.app.constant;

public interface PagesConstant {
    String JSP_PAGES_LOCATION = "/WEB-INF/jsp";
    String EMAIL_VALIDATION_REGEX = "^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$";
    String LATIN_VALIDATION_REGEX = "[a-zA-Z]{3,40}$";
    String REVIEW_VALIDATION_REGEX = "[a-zA-Z]{1,180}$";
    String TOPIC_VALIDATION_REGEX = "[a-zA-Z]{1,30}$";
    String USERS_URL_VALUE = "/users";
    String REVIEWS_URL_VALUE = "/reviews";
}
