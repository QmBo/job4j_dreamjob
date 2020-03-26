<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<c:set var="URL" value="${pageContext.servletContext.contextPath}"/>
<html>
<head>
    <title>Users List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu"
          crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>
        function update(link) {
            location.href = link;
        }
    </script>
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
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
            <h2>Users List</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-1">
            <c:if test="${admin}">
                <a href="${URL}/create" class="btn btn-success" title="Create user">Create user</a>
            </c:if>
        </div>
        <div class="col-md-1 col-md-offset-10">
            <a href="${URL}/logout" class="btn btn-warning" title="Logout">Logout</a>
        </div>
    </div>
    <%--@elvariable id="users" type="java.util.Set"--%>
    <c:set var="usersEmpty" value="${fn:length(users) == 0}"/>
    <c:if test="${!usersEmpty}">
        <div class="row" style="margin-top: 10px">
            <table class="table table-hover">
                <tr>
                    <th>Id</th>
                    <th>Photo</th>
                    <th>Name</th>
                    <th>Login</th>
                    <th>Email</th>
                    <th>Country</th>
                    <th>City</th>
                    <th>Role</th>
                    <th>Create date</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${usersList}" var="user">
                    <tr  title="Edit user" onclick="update('${URL}/edit?id=${user.id}')">
                        <td>${user.id}</td>
                        <td>
                            <a href="${URL}/download?name=${user.photoId}">
                                <img title="Download image" src="${URL}/download?name=${user.photoId}" width="100px" height="100px" alt="Download"/>
                            </a>
                        </td>
                        <td>${user.name}</td>
                        <td>${user.login}</td>
                        <td>${user.email}</td>
                        <td>${user.country}</td>
                        <td>${user.city}</td>
                        <td>${user.role.name}</td>
                        <td>${user.createDate}</td>
                        <td>
                            <form action="${URL}/" method="post">
                                <input type="hidden" name="del" value="${user.id}">
                                <input title="Delete user" class="btn btn-danger" type="submit" value="Delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
    <c:if test="${usersEmpty}">
        <div class="row">
            <div class="col-md-4">
                <h2>Users not found</h2>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
