<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="URL" value="${pageContext.servletContext.contextPath}"/>
<html>
<head>
    <title>Create User</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu"
          crossorigin="anonymous">
<%--suppress JSUnresolvedLibraryURL --%>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>
        var host = '192.168.1.101';
        var port = '8080';

        function validate() {
            var result = true;
            var message = "";
            var array = [];
            array[0] = $("#name");
            array[1] = $("#login");
            array[2] = $("#email");
            array[3] = $("#password");
            array.forEach(function (inputVal) {
                if (inputVal.val() === '') {
                    message += " ";
                    message += inputVal[0].name;
                    result = false;
                }
            });
            if (message !== "") {
                alert("Enter" + message + "!");
            }
            return result;
        }
        function citys() {
            var country = $('#country').val();
            if (country !== null) {
                $.ajax({
                    url: 'http://' + host + ':' + port + '/servlet/address',
                    type: 'GET',
                    data: 'name=' + country,
                    dataType: 'text'
                }).done(function(data) {
                    setCode(data, '#city');
                }).fail(function(err){
                    alert(err);
                });
            }
        }

        function setCode(data, selectId) {
            var pars = $.parseJSON(data);
            pars.e.forEach(function(element){
                var tags = '<option value="' + element.id + '">' + element.name + '</option>';
                $(selectId).append(tags);
            })
        }

        $().ready(function country() {
            $.ajax({
                url: 'http://' + host + ':' + port + '/servlet/address',
                type: 'GET',
                data: 'name=allCountrys',
                dataType: 'text'
            }).done(function (data) {
                setCode(data, '#country');
                citys();
            }).fail(function (err) {
                alert(err);
            });
            $('#country').change(function() {
                    var country = $('#country').val();
                    if (country != null) {
                        $('#city').empty();
                        citys()
                    }
                }
            );
            $('#submit').attr('disabled', 'disabled');
            $( "#login" ).blur(function() {
                var login = $('#login').val();
                if(login.length !== 0) {
                    available(login);
                }
            });
        });

        function available(login) {
            var port = '8080';
            $.ajax({
                url: 'http://' + host + ':' + port + '/servlet/available',
                type: 'GET',
                data: 'login=' + login,
                dataType: 'text'
            }).done(function (data) {
                var pars = $.parseJSON(data);
                if (pars.login) {
                    $('#submit').removeAttr('disabled');
                    $('#error').removeClass('has-error');
                    $('.glyphicon').addClass('sr-only');
                    $('#inputLoginError').addClass('sr-only');
                } else {
                    $('#submit').attr('disabled', 'disabled');
                    $('#error').addClass('has-error');
                    $('.glyphicon').removeClass('sr-only');
                    $('#inputLoginError').removeClass('sr-only');
                }
            }).fail(function (err) {
                alert(err);
            });
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
            <h2>Create Users</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-1">
            <a href="${URL}/" class="btn btn-warning" title="Users List">Users List</a>
        </div>
        <div class="col-md-1 col-md-offset-10">
            <a href="${URL}/logout" class="btn btn-warning" title="Logout">Logout</a>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form class="form-horizontal" action="${URL}/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">User name:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="name" name="name" placeholder="User name">
                    </div>
                    <label for="login" class="col-sm-2 control-label">User login:</label>
                    <div id="error" class="col-sm-10 has-feedback">
                        <input type="text" class="form-control" id="login"
                               name="login" placeholder="User login" aria-describedby="inputError2Status">
                        <span class="glyphicon glyphicon-remove form-control-feedback sr-only" aria-hidden="true"></span>
                        <span id="inputLoginError" class="sr-only">Login is already used!</span>
                    </div>
                    <label for="password" class="col-sm-2 control-label">User password:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" name="password" placeholder="User password">
                    </div>
                    <label for="email" class="col-sm-2 control-label">User email:</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" id="email" name="email" placeholder="User email">
                    </div>
                    <label for="country" class="col-sm-2 control-label">User country:</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="country" id="country">
                        </select>
                    </div>
                    <label for="city" class="col-sm-2 control-label">User city:</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="city" id="city">
                        </select>
                    </div>
                    <label for="role" class="col-sm-2 control-label">User role:</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="role" id="role">
                            <%--@elvariable id="roleList" type="java.util.List"--%>
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.id}">
                                    <c:out value="${role.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="file" class="col-sm-2 control-label">User image:</label>
                    <div class="col-sm-10">
                        <input type="file" id="file" name="file" accept="image/*">
                    </div>
                    <div class="col-sm-offset-2 col-sm-10">
                        <button id="submit" type="submit" class="btn btn-default" onclick="return validate()" title="Create user">Create</button>
                    </div>
                    <div class="col-sm-offset-2 col-sm-10">
                        <h4>All fields are required!</h4>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
