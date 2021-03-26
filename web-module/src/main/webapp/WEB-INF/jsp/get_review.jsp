<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>ReviewsList</title>
</head>
<body>
<div align="center">
    <form method="post" action="${pageContext.request.contextPath}/success">
        <table border="1" cellpadding="5">
            <caption><h2>List of reviews</h2></caption>
            <tr>
                <th>topic</th>
                <th>review</th>
                <th>date</th>

                <th>to delete</th>
            </tr>
            <c:forEach var="review" items="${reviews}">
            <tr>
                <td><c:out value="${review.topic}"/></td>
                <td><c:out value="${review.review}"/></td>
                <td><c:out value="${review.date}"/></td>
                <td>
                    <input type="checkbox" name="userId" value="${user.id}">
                </td>
                </c:forEach>
        </table>
        <br>
        <td colspan="2" align="right">
            <input type="submit" value="Delete selected users"/>
        </td>
    </form>
    <form action="${pageContext.request.contextPath}/add-review">
        <input type="submit" value="Add review">
    </form>
</div>
</body>
</html>