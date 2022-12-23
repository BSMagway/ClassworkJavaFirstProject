package org.example.servlets;

import org.example.jdbc.dao.UsersDAO;
import org.example.jdbc.dao.impl.UserDAOImpl;
import org.example.model.User;
import org.example.util.HTMLTableBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {


    private static List<String> headers = Arrays.asList("ID", "EMAIL", "NAME", "OFFICE_ID", "DETAILS", "IS_ACTIVE", "CREATED_TS", "LAST_UPDATED_TS");
    private static UsersDAO dao = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<User> allUsers = dao.getAllUser();

//        HTMLTableBuilder table = new HTMLTableBuilder("Crazy Users List", true, headers.size(), 8, 8 , 8);
//        table.addTableHeader("ID", "EMAIL", "NAME", "OFFICE_ID", "DETAILS", "IS_ACTIVE", "CREATED_TS", "LAST_UPDATED_TS");
//        for (User user : allUsers) {
//
//            table.addRowValues(user.getId()+"", user.getEmail(), user.getName(), "1", user.getDetails(), user.isActive()+"", "DATE1", "DATE2" );
//        }
//        resp.setContentType("text/html");
//        resp.setCharacterEncoding("utf-8");
//        resp.getWriter().println(table.build());

        req.setAttribute("users",allUsers ); // Set<User> set = (Set<User>)req.getAtt("users");

    }
}
