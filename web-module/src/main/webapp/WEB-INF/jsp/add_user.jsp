<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/add-user" method="POST">
    <p><b>Enter lastName:</b><br>
        <label>
            <input minlength="3" maxlength="40" name="lastName" type="text">
        </label></p>
    <p><b>Enter firstName:</b><br>
        <label>
            <input minlength="3" maxlength="20" name="firstName" type="text">
        </label></p>
    <p><b>Enter patronymic:</b><br>
        <label>
            <input minlength="3" maxlength="40" name="patronymic" type="text">
        </label></p>
    <p><b>Enter email:</b><br>
        <label>
            <input minlength="3" maxlength="50" name="email" type="text">
        </label></p>
    <p><b>Enter password:</b><br>
        <label>
            <input minlength="3" maxlength="10" name="password" type="password"
        </label></p>
    <b>Role:</b><br>
    <label>
        <select name="role" size="1">
            <option>ROLE_USER</option>
            <option>ROLE_ADMINISTRATOR</option>
        </select>
    </label>
    <br>
    <form action="${pageContext.request.contextPath}/add-user" method="POST">
        <input value="Add User!" type="submit">
    </form>
</form>
</body>
</html>
