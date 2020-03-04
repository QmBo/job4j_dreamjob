<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Users List</title>
</head>
<body>
<c:set var="URL" value="${pageContext.servletContext.contextPath}"/>
<form action="${URL}/create">
    <button>Create user</button>
</form>
<%--@elvariable id="users" type="java.util.Set"--%>
<c:set var="usersEmpty" value="${fn:length(users) == 0}"/>
<c:if test="${!usersEmpty}">
    <table border="1">
        <tr>
            <th>Id</th>
            <th>Photo</th>
            <th>Name</th>
            <th>Login</th>
            <th>Email</th>
            <th>Create date</th>
            <th>Update</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>
                    <a href="${URL}/download?name=${user.photoId}">
                    <img src="${URL}/download?name=${user.photoId}" width="100px" height="100px"/>
                    </a>
                </td>
                <td>${user.name}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.createDate}</td>
                <td>
                    <form action="${URL}/edit" method="get">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" value="Update">
                    </form>
                </td>
                <td>
                    <form action="${URL}/" method="post">
                        <input type="hidden" name="del" value="${user.id}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${usersEmpty}">
    <h2>Users not found</h2>
</c:if>
</body>
</html>
