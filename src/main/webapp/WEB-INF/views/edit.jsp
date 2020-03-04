<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<c:set var="URL" value="${pageContext.servletContext.contextPath}"/>
<%--@elvariable id="user" type="ru.job4j.servlets.userservlet.User"--%>
<c:if test="${user == null}">
    <h2>Users not found</h2>
</c:if>
<c:if test="${user != null}">
    <form action="${URL}/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${user.id}">
        <table>
            <tr>
                <td>User name: </td>
                <td><input type="text" name="name" value="${user.name}" title="User name"></td>
            </tr>
            <tr>
                <td>User login: </td>
                <td><input type="text" name="login" value="${user.login}" title="User login"></td>
            </tr>
            <tr>
                <td>User email: </td>
                <td><input type="email" name="email" value="${user.email}" title="User email"></td>
            </tr>
            <tr>
                <td>User image: </td>
                <td>
                    <img src="${URL}/download?name=${user.photoId}" width="100px" height="100px"/>
                    <input type="hidden" name="oldPhotoId" value="${user.photoId}">
                    <input type="file" name="file" accept="image/*">
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Update"></td>
            </tr>
        </table>
    </form>
</c:if>
<form action="${URL}/">
    <button>Users List</button>
</form>
</body>
</html>
