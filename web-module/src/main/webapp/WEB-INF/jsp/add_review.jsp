<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Add review</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/add-review" method="POST">

    <p><b>Enter topic:</b><br>
        <label>
            <input minlength="3" maxlength="30" name="topic" type="text">
        </label></p>
    <p><b>Enter review</b><br>
        <label>
            <input minlength="3" maxlength="180" name="review" type="text">
        </label></p>
    <br>
        <input value="Add Review!" type="submit">
</form>
</body>
</html>