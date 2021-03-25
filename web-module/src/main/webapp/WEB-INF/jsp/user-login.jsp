<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Access page</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login-control">
    <table border="1" cellpadding="5" cellspacing="2">
        <thead>
        <tr>
            <th colspan="2">Login please!</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Email</td>
            <td><input minlength="3" maxlength="20" type="text" name="email" required/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input minlength="1" maxlength="10" type="password" name="password" required/></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Login"/>
                <input type="reset" value="Reset"/>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
