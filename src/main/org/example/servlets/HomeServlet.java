package org.example.servlets;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("init job");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("ServletPath - > " + req.getServletPath());
        System.out.println("getContextPath - > " + req.getContextPath());
        System.out.println("getRequestURI - > " + req.getRequestURI());
        System.out.println("getPathInfo - > " + req.getPathInfo());

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

//        PrintWriter writer = resp.getWriter();

//        // request parameter
//        String name = req.getParameter("name");
//
//
//        writer.println("<h1>Hi "+ (name == null? "Stranger" : name)
//                +" from Tomcat Servlet . Server time:</h1>" + new Date());
//
//        writer.close();

        RequestDispatcher disp = req.getRequestDispatcher("jsp/home.jsp");
        disp.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("destroy job");
    }
}
