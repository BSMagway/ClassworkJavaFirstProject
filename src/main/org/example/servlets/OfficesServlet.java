package org.example.servlets;

import org.example.jdbc.dao.impl.OfficesDAOImpl;
import org.example.model.Office;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/offices")
public class OfficesServlet  extends HttpServlet {

    private OfficesDAOImpl dao = new OfficesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       RequestDispatcher rd = req.getRequestDispatcher("/jsp/listOffices.jsp");
       String actions = req.getParameter("action");
       if(actions == null){
           rd.forward(req, resp);
           return;
       }
       String officeId = req.getParameter("officeId");

       switch (actions){
           case "D":
               boolean isDeleted = dao.delete(new Office(Integer.parseInt(officeId)));
               req.setAttribute("msg", !isDeleted ? "Can't delete Office #" + officeId : "");
               rd.forward(req, resp);
               return;
           case "U":
               Office office = dao.getById(new Office(Integer.parseInt(officeId)));
               req.setAttribute("office", office);
               RequestDispatcher rd2 = req.getRequestDispatcher("/jsp/UpdateOffice.jsp");
               rd2.forward(req, resp);

           case "C":

               RequestDispatcher rd3 = req.getRequestDispatcher("/jsp/createOffice.jsp");
               rd3.forward(req, resp);
       }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/jsp/listOffices.jsp");
        String action = req.getParameter("action");


        switch (action){
            case "U":
              Office office = new Office();
                int id = Integer.parseInt(req.getParameter("id"));
                String location = req.getParameter("location");
                String ph1 = req.getParameter("ph1");
                String ph2 = req.getParameter("ph2");

                office.setId(id);
                office.setLocation(location);
                office.setPhone1(ph1);
                office.setPhone2(ph2);

              boolean isUpdated = dao.update(office);

                req.setAttribute("msg",  "Office with Id#" + id + " was" + (isUpdated? "": "NOT")+" upated.");
                rd.forward(req, resp);
                return;
            case "C":
                String newLocation = req.getParameter("location");
                String newPh1 = req.getParameter("ph1");
                String newPh2 = req.getParameter("ph2");

                Office newO = new Office();
                newO.setLocation(newLocation);
                newO.setPhone1(newPh1);
                newO.setPhone2(newPh2);

                boolean isCreated = dao.create(newO);
                req.setAttribute("msg",  "Office with locatiomn " + newLocation + " was" + (isCreated? "": "NOT")+" created.");
                rd.forward(req, resp);
            return;
        }

    }
}
