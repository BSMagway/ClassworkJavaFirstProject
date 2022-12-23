package org.example.servlets;

import org.example.jdbc.dao.UsersDAO;
import org.example.jdbc.dao.impl.UserDAOImpl;
import org.example.model.User;
import org.example.util.EncryptDecryptUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("LoginForm.html");
        rd.forward(req, resp);

    }

    private static UsersDAO dao = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        String email = req.getParameter("email");
        String pwd = EncryptDecryptUtils.encrypt(req.getParameter("pwd"));

        User user = dao.getUserByEmail(email);

        if(user == null){
            RequestDispatcher disp = req.getRequestDispatcher("LoginForm.html");

            resp.getWriter().println("Ooops! User does not exist. <a href='regme'>REGISTRATION<a> ");
            disp.include(req, resp);
        } else if(!pwd.equals(user.getPwd())) {
            RequestDispatcher disp = req.getRequestDispatcher("LoginForm.html");
            resp.getWriter().println("Ooops! Wrong password.<a href='reset'>RESET PASSWORD<a>");
            disp.include(req, resp);
        } else {

            HttpSession session = req.getSession();
            // session.setMaxInactiveInterval(5);
            System.out.println("session id is " + session.getId());
            session.setAttribute("user", user);


            RequestDispatcher disp = req.getRequestDispatcher("jsp/home.jsp");
            disp.forward(req, resp);
        }

    }
}
