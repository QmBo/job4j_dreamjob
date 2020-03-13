<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="URL" value="${pageContext.servletContext.contextPath}"/>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<form action="${URL}/logout">
    <button>Logout</button>
</form>
<%--@elvariable id="user" type="ru.job4j.servlet.logic.User"--%>
<c:if test="${user == null}">
    <h2>Users not found</h2>
</c:if>
<c:if test="${user != null}">
    <form action="${URL}/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${user.id}">
        <table>
            <tr>
                <td>User name: </td>
                <td><input required  type="text" name="name" value="${user.name}" title="User name"></td>
            </tr>
            <tr>
                <td>User login: </td>
                <td><input required  type="text" name="login" value="${user.login}" title="User login"></td>
            </tr>
            <tr>
                <td>User email: </td>
                <td><input required  type="email" name="email" value="${user.email}" title="User email"></td>
            </tr>
            <tr>
                <td>User password: </td>
                <td><input required  type="password" name="password" title="User password"></td>
            </tr>
                <%--@elvariable id="userRole" type="java.lang.String"--%>
            <c:if test="${userRole == 'Administrator'}">
                <tr>
                    <td>User role: </td>
                    <td>
                        <select name="role">
                                <%--@elvariable id="roleList" type="java.util.List"--%>
                            <c:forEach var="role" items="${roleList}">
                                <c:if test="${user.role.id == role.id}">
                                    <option selected value="${role.id}">
                                </c:if>
                                <c:if test="${user.role.id != role.id}">
                                    <option value="${role.id}">
                                </c:if>
                                    <c:out value="${role.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>User image: </td>
                <td>
                    <img src="${URL}/download?name=${user.photoId}" width="100px" height="100px" alt="Old image"/>
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
