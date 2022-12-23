package org.example.filters;

import org.example.model.User;
import org.example.util.HttpServletUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        User sessionUser = HttpServletUtil.getSessionUser(req);


        System.out.println(Thread.currentThread().getId() + " | "
                +Thread.currentThread().getName() +" | " +  req.getContextPath() + req.getServletPath()+ " | " + new Date() + " | "+
                (sessionUser != null ? sessionUser.getEmail() : "")
        );

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
