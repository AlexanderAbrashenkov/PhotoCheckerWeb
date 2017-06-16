<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<html>
<head>
    <title>Заголовок</title>
    <link type="text/css" href="<@spring.url "/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<div id="welcomeTitle">Добро пожаловать!</div>
<div class="welcomeButton" onclick="window.location = 'reports'">
    <div class="welButRep">Список отчетов по просмотру фотографий</div>
    <div class="welButClose">(Закрытый раздел)</div>
</div>
<div class="blank"></div>
<div class="welcomeButton" onclick="window.location = 'route'">
    <div class="welButRep">Построение оптимального маршрута</div>
    <div class="welButClose">(Закрытый раздел)</div>
</div>
</body>
</html>