package org.example.util;

import org.example.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpServletUtil {

    public static final String SESSION_USER = "user";

    public static User getSessionUser(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        System.out.println(httpSession.getId());

        Object userObject = httpSession.getAttribute(SESSION_USER);
        return userObject == null ? null : (User)userObject;

    }
}
