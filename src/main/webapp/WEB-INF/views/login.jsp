<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign in</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu"
          crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <%--@elvariable id="error" type="java.lang.String"--%>
            <c:if test="${error != ''}">
                <div class="text-center">
                    <p class="bg-danger">
                        <c:out value="${error}"/>
                    </p>
                </div>
            </c:if>
            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="login" class="col-sm-2 control-label">Login</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="login" name="login" placeholder="Login">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Sign in</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
