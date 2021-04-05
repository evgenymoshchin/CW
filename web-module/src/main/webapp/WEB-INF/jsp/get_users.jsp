<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>UsersList</title>
</head>
<body>
<div align="center">
    <form method="post" action="${pageContext.request.contextPath}/users-deleted">
        <table border="1" cellpadding="5">
            <caption><h2>List of users</h2></caption>
            <tr>
                <th>firstName</th>
                <th>lastName</th>
                <th>patronymic</th>
                <th>email</th>
                <th>role</th>
                <th>to delete</th>
            </tr>

            <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.patronymic}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td>
                    <label>
                        <input type="checkbox" name="userId" value="${user.id}">
                    </label>
                </td>
                </c:forEach>
        </table>
        <br>
        <td colspan="2" align="right">
            <input type="submit" value="Delete selected users"/>
        </td>
    </form>
    <form action="${pageContext.request.contextPath}/add-user">
        <input type="submit" value="Add user">
    </form>
</div>
</body>
</html>