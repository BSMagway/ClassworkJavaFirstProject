package org.example.servlets;

import org.example.jdbc.dao.UsersDAO;
import org.example.jdbc.dao.impl.UserDAOImpl;
import org.example.model.User;
import org.example.util.EncryptDecryptUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activate")
public class ActivateServlet extends HttpServlet {
    UsersDAO dao = new UserDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String email = EncryptDecryptUtils.decrypt(token);
        User user = dao.getUserByEmail(email);
        if( user != null && !user.isActive()){
            dao.activateUser(email);
            resp.sendRedirect("LoginForm.html");
        } else {
            resp.getWriter().println("Something wrong!.");
        }

    }
}
