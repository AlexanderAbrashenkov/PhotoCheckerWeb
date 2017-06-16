<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<html>
<head>
    <title>Авторизация</title>
    <link type="text/css" href="<@spring.url "/resources/css/style.css"/>" rel="stylesheet">
</head>
<body>
<div id="login_block">
    <form action="/login" id="login_form" method="post">
        <div class="login_title">Пожалуйста, авторизуйтесь!</div>
        <input type="text" class="login_input" name="login_login" placeholder="Логин"><br>
        <input type="password" class="login_input" name="login_password" placeholder="Пароль"><br>
        <input type="submit" value="Войти" class="submit_button">
    </form>
    <#if error>
        <div class="errorInfo">
            Пользователя с таким логином или паролем не существует!
        </div>
    </#if>
    <div class="information">Если у Вас еще нет логина и пароля, обратитесь к своему непосредственному руководителю.
    </div>
</div>
</body>
</html>