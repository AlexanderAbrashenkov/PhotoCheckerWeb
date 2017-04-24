<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="h" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Авторизация</title>
    <link type="text/css" href="css/style.css" rel="stylesheet">
    <script src="userName.js" style="text/javascript"></script>
</head>
<body>
<div id="login_block">
    <form action="/login" id="login_form" method="post">
        <div class="login_title">Пожалуйста, авторизуйтесь!</div>
        <input type="text" class="login_input" name="login_login" placeholder="Логин"><br>
        <input type="password" class="login_input" name="login_password" placeholder="Пароль"><br>
        <input type="submit" value="Войти" class="submit_button">
    </form>
    <h:if test="${error}">
        <div class="errorInfo">
            Пользователя с таким логином или паролем не существует!
        </div>
    </h:if>
    <div class="information">Если у Вас еще нет логина и пароля, обратитесь к своему непосредственному руководителю.
    </div>
</div>
</body>
</html>