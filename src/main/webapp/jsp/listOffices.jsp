<%@page import="java.util.Set" %>
<%@page import="org.example.model.Office" %>
<%@page import="org.example.jdbc.dao.impl.OfficesDAOImpl" %>
<%@ page import="org.example.model.User" %>
<%@ page import="org.example.util.HttpServletUtil" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>

</head>
<body>

<div id="header">
    <jsp:include page="header.jsp"></jsp:include>
</div>

<h2>Offices</h2>
<h4><%=request.getAttribute("msg") == null ? "" : request.getAttribute("msg")%>
</h4>
<%

    User user = HttpServletUtil.getSessionUser(request);
    if (user == null) { %>
<div id="no-acc">
    <jsp:include page="noAccess.jsp"></jsp:include>
</div>
<%} else {

Set <Office> offices = new OfficesDAOImpl().getAll(); %>


    <table>
        <tr>
            <th>ID</th>
            <th>LOCATION</th>
            <th>PHONE #1</th>
            <th>PHONE #2</th>
            <th>UPDATE</th>
            <th>DELETE</th>
        </tr>
        <% for (Office office : offices) { %>
        <tr>
            <td><%= office.getId()%>
            </td>
            <td><%= office.getLocation()%>
            </td>
            <td><%= office.getPhone1()%>
            </td>
            <td><%= office.getPhone2()%>
            </td>

            <td><a href="offices?action=U&officeId=<%=office.getId() %>">UPDATE</a></td>
            <td><a href="offices?action=D&officeId=<%=office.getId() %>">DELETE</a></td>
        </tr>
        <%}%>

    </table>
    <a href="offices?action=C">CREATE</a>
 <%}%>

</body>
</html>