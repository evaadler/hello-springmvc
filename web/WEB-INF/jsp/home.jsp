<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: fifi
  Date: 2018/10/21
  Time: 10:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spitrr</title>
</head>
<body>
<h1>Welcome to Spittr</h1>

<a href="<c:url value="/spittles" />">Spittles</a>
<a href="<c:url value="/spittles/register" />">Register</a>
</body>
</html>
