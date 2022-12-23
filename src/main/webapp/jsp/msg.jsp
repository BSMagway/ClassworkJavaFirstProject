<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@page isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Message</title>
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

<c:if test="${empty msg2}">
    <h5>${msg2}</h5>
</c:if>
<c:out value='${requestScope.get("msg2")}'></c:out>
<h5>${msg2}</h5>
<c:forEach var="i" begin="1" end="5" >
    <h5>1111</h5>
</c:forEach>
<div id="footer">
    <jsp:include page="footer.jsp"></jsp:include>
</div>

</body>
</html>