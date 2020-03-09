<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%--@elvariable id="error" type="java.lang.String"--%>
<c:if test="${error != ''}">
    <div style="background-color: red"><c:out value="${error}"/></div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/login" method="post">
    <table>
        <tr>
            <td>Login</td>
            <td><input type="text" name="login"></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password"></td>
        </tr>
    </table>
    <input type="submit" value="Login">
</form>
</body>
</html>
