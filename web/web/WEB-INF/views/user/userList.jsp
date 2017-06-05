<%--
  Created by IntelliJ IDEA.
  User: diyaguang
  Date: 2017/6/5
  Time: 上午11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Show users</title>
</head>
<body>
<h1>Show users</h1>
<a href="<c:url value="/" />">Home</a> |
<a href="<c:url value="/user/register" />">Register</a>
<br>
<h4> User List </h4>
<br/>
<br/>
<c:forEach items="${userList}" var="user">
    <li id="user_<c:out value="user.id"/> ">
        <div>
            <a href="/user/register/<c:out value="${user.id}"/>"><c:out value="${user.username}"/></a>
        </div>
        <div>
            <span class="userpwd"><c:out value="${user.userpwd}"/> </span>
        </div>
    </li>
</c:forEach>
</body>
</html>
