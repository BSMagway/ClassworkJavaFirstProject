package org.example.servlets;

import org.example.jdbc.dao.UsersDAO;
import org.example.jdbc.dao.impl.UserDAOImpl;
import org.example.model.User;
import org.example.util.EncryptDecryptUtils;
import org.example.util.IOUtils;
import org.example.util.MailUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/regme")
public class RegServlet extends HttpServlet {

    private static final UsersDAO dao = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        final String email = req.getParameter("email");
        String pwd = EncryptDecryptUtils.encrypt(req.getParameter("pwd"));
        String confirmPwd = EncryptDecryptUtils.encrypt(req.getParameter("confirmPwd"));
        String name = req.getParameter("name");
        String details = req.getParameter("details");

        if (pwd.equals(confirmPwd)) {
            User user = new User(email, pwd, name, details);
            dao.createUser(user);
            String content = IOUtils.readFileBuff("C:\\Users\\Sharlan_A\\IdeaProjects\\webapp\\src\\main\\webapp\\templates\\ActivationTemplate.html");
            content = content.replace("<&>", EncryptDecryptUtils.encrypt(email));

            final String finalContent = content;

            new Thread(new Runnable(){
                @Override
                public void run() {
                    MailUtils.send(email, "WELCOME to CRAZY USERS APP", finalContent, null );
                }
            }).start();



            resp.getWriter().println(email + " has been registered but NOT ACTIVE. Please check your mailbox in few minutes.!"); // ??
        } else {
            RequestDispatcher reqDisp = req.getRequestDispatcher("RegistrationForm.html");
            resp.getWriter().println("Passwords are not equal");
            reqDisp.include(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher reqDisp = req.getRequestDispatcher("RegForm.html");
        reqDisp.forward(req, resp);
    }
}
