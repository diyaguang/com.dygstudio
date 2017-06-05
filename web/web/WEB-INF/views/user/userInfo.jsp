<%--
  Created by IntelliJ IDEA.
  User: diyaguang
  Date: 2017/6/5
  Time: 上午11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>User info</title>
</head>
<body>
    <c:if test="${user != null}" var="result" scope="page">
        <h1>Hello <c:out value="${user.username}"/> ,please check you info </h1>
    </c:if>
    <c:if test="${!result}">
        <h1>Please input new user info </h1>
    </c:if>

    <a href="<c:url value="/" />">Home</a> |
    <a href="<c:url value="/user" />">UserList</a>

    <form method="post">
        user name:<input type="text" name="userName"/><br/>
        user password:<input type="password" name="userpwd"/><br/>
        <c:if test="${result}"><input type="submit" value="Submit"/></c:if>
        <c:if test="${!result}"><input type="submit" value="Register"/></c:if>
    </form>

</body>
</html>
