<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<c:set var="URL" value="${pageContext.servletContext.contextPath}"/>
<html>
<head>
    <title>Users List</title>
</head>
<body>
<form action="${URL}/logout">
    <button>Logout</button>
</form>
<c:forEach items="${users}" var="user">
    <%--@elvariable id="login" type="java.lang.String"--%>
    <c:if test="${user.login == login}">
        <%--@elvariable id="userRole" type="java.lang.String"--%>
        <c:if test="${userRole == 'User'}">
            <c:set var="usersList" value="${[user]}"/>
            <c:set var="admin" value="${false}"/>
        </c:if>
        <c:if test="${userRole == 'Administrator'}">
            <c:set  var="usersList" value="${users}"/>
            <c:set var="admin" value="${true}"/>
        </c:if>
    </c:if>
</c:forEach>
<c:if test="${admin}">
    <form action="${URL}/create">
        <button>Create user</button>
    </form>
</c:if>

<%--@elvariable id="users" type="java.util.Set"--%>
<c:set var="usersEmpty" value="${fn:length(users) == 0}"/>
<c:if test="${!usersEmpty}">
    <table>
        <style type="text/css">
            TABLE {
                border-collapse: collapse;
            }
            TH, TD {
                border: 1px solid black;
                text-align: center;
                padding: 4px;
            }
            TH {
                background: #fc0;
                vertical-align: center;
                padding: 0;
            }
        </style>
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
        <c:forEach items="${usersList}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>
                    <a href="${URL}/download?name=${user.photoId}">
                    <img src="${URL}/download?name=${user.photoId}" width="100px" height="100px" alt="Download"/>
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
