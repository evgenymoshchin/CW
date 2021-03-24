<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>UsersList</title>
</head>
<body>
<h1 style="text-align:center;"> Welcome to users file!</h1>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of users</h2></caption>
        <tr>
            <th>id</th>
            <th>firstName</th>
            <th>lastName</th>
            <th>patronymic</th>
            <th>email</th>
            <th>password</th>
            <th>role</th>
        </tr>
        <c:forEach var="user" items="${users}">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.firstName}"/></td>
            <td><c:out value="${user.lastName}"/></td>
            <td><c:out value="${user.patronymic}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.password}"/></td>
            <td><c:out value="${user.role}"/></td>
            </c:forEach>
    </table>
    <br>
    <form action="logout-control" method="post">
        <input type="submit" value="Logout">
    </form>
</div>
</body>
</html>