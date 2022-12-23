<%@ page import="org.example.model.Office" %><%--
  Created by IntelliJ IDEA.
  User: sharlan_a
  Date: 24.11.2022
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Office</title>
</head>
<body>

    <form action="offices?action=U" method="post">
        <label for="id">ID:</label><br>
        <input type="text" id="id" name="id" value="<%=((Office)request.getAttribute("office")).getId()%>" readonly><br>

        <label for="location">Location:</label><br>
        <input type="text" id="location" name="location" value="<%=((Office)request.getAttribute("office")).getLocation()%>" required><br>

        <label for="ph1">Phone #1:</label><br>
        <input type="text" id="ph1" name="ph1" value="<%=((Office)request.getAttribute("office")).getPhone1()%>" required><br>

        <label for="ph2">Phone #2:</label><br>
        <input type="text" id="ph2" name="ph2" value="<%=((Office)request.getAttribute("office")).getPhone2()%>"><br>

      <input type="submit" value="UPDATE">
    </form>

</body>
</html>
