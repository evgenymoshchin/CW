package com.gmail.evgenymoshchin.app.filters;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class AccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<RoleEnum, String> roleMap = new HashMap<RoleEnum, String>() {{
        put(RoleEnum.ROLE_ADMINISTRATOR, "/users");
        put(RoleEnum.ROLE_USER, "/roles");
    }};

    public static String getRedirectionUrl(RoleEnum roleName) {
        return roleMap.get(roleName);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
