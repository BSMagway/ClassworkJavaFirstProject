<%--
  Created by IntelliJ IDEA.
  User: sharlan_a
  Date: 25.11.2022
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Office</title>
</head>
<body>
<form action="offices?action=C" method="post">

  <label for="location">Location:</label><br>
  <input type="text" id="location" name="location"  required><br>

  <label for="ph1">Phone #1:</label><br>
  <input type="text" id="ph1" name="ph1" required><br>

  <label for="ph2">Phone #2:</label><br>
  <input type="text" id="ph2" name="ph2" ><br>

  <input type="submit" value="CREATE">
  </form>
</body>
</html>
