<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Маршруты</title>
    <link type="text/css" href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <link type="text/css" href="<c:url value="/resources/css/route.css" />" rel="stylesheet">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
</head>

<body>
<div id="head_pane">
    <div class="report_title">
        <a href="/" style="border: 0;">Главная</a> /
    </div>
    <div id="filter_pane">
        <div id="logoff_pane">
                        <span id="lbl_user_name">${Session.user.userName}
                            <input type="button" id="exit" value="Выход" onclick="window.location = '/logoff';"><br>
                        </span>
        </div>
    </div>
</div>
<div id="top_separate"></div>
<div id="floating-panel">
    <strong>Начало:</strong>
    <div id="box">
        <input type="text" id="start">
        <br>
    </div>
    <strong>Конец:</strong>
    <div>
        <input type="text" id="end">
    </div>
    <strong>Промежуточные точки:
        <input type="checkbox" id='che' onchange = 'showOrHide("che", "chet")'>
    </strong>
    <div>
        <textarea placeholder="Максимум 23 промежуточных точек. Для разделения адресов использовать - ;" id="chet" style='display: none'></textarea>
    </div>
    <div id="centr">
        <button id="but" onclick="zap();">Построить маршрут</button>
        <a id="a" href="route.csv" download="route.csv"><button id="butSav" onclick="sav(1);">Сохранить маршрут</button></a>
    </div>
    <img>
</div>
<div id="right-panel">
</div>
<div id="map"></div>
<script src="<@spring.url "/resources/js/route.js"/>"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCqy7bZuU0AFm5UsnZXNB0JOGP5S3UEoVM&callback=initMap">

</script>
</body>

</html>