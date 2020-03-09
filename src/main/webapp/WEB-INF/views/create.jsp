<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="URL" value="${pageContext.servletContext.contextPath}"/>
<html>
<head>
    <title>Create User</title>
</head>
<body>
<form action="${URL}/logout">
    <button>Logout</button>
</form>
<form action="${URL}/create" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>User name: </td>
            <td><input required  type="text" name="name" placeholder="User name"></td>
        </tr>
        <tr>
            <td>User login: </td>
            <td><input required type="text" name="login" placeholder="User login"></td>
        </tr>
        <tr>
            <td>User password: </td>
            <td><input required  type="password" name="password" placeholder="User password"></td>
        </tr>
        <tr>
            <td>User email: </td>
            <td><input required  type="email" name="email" placeholder="User email"></td>
        </tr>
        <tr>
            <td>User role: </td>
            <td>
                <select name="role">
                    <%--@elvariable id="roleList" type="java.util.List"--%>
                    <c:forEach var="role" items="${roleList}">
                        <option value="${role.id}">
                            <c:out value="${role.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>User image: </td>
            <td><input type="file" name="file" accept="image/*"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Create"></td>
        </tr>
    </table>
</form>
</body>
</html>
