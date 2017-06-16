<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>${pageTitle}</title>

    <link type="text/css" href="<@spring.url "/resources/css/style.css"/>" rel="stylesheet">
    <#if pageCategory == 'lka'>
        <link type="text/css" href="<@spring.url "/resources/css/lkaStyle.css"/>" rel="stylesheet">
    </#if>
    <#if pageCategory == 'mlka'>
        <link type="text/css" href="<@spring.url "/resources/css/mlkaStyle.css"/>" rel="stylesheet">
    </#if>
    <#if pageCategory == 'lkaDmp'>
        <link type="text/css" href="<@spring.url "/resources/css/lkaDmpStyle.css"/>" rel="stylesheet">
    </#if>
    <#if pageCategory == 'administrate'>
        <link type="text/css" href="<@spring.url "/resources/css/administrateStyle.css"/>" rel="stylesheet">
    </#if>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="<@spring.url "/resources/js/main.js"/>" type="text/javascript"></script>
    <#if pageCategory == 'lka'>
        <script src="<@spring.url "/resources/js/lka.js"/>" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'mlka'>
        <script src="<@spring.url "/resources/js/mlka.js"/>" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'lkaDmp'>
        <script src="<@spring.url "/resources/js/lkaDmp.js"/>" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'reports'>
        <script src="<@spring.url "/resources/js/reports.js"/>" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'administrate'>
        <script src="<@spring.url "/resources/js/administrate.js"/>" type="text/javascript"></script>
    </#if>
</head>
<body>
<div id="errorBlock">Произошла ошибка! Невозможно подключиться к серверу. Попробуйте позднее!</div>
<div id="savedSuccessfullyBlock">Данные успешно сохранены!</div>
<div id="head_pane">
    <div class="report_title">
        <a href="/" style="border: 0;">Главная</a> /
        <a href="/reports" style="border: 0;">Отчеты</a>
    </div>
    <div id="filter_pane">
        <div id="logoff_pane">
            <span id="lbl_user_name">${Session.user.userName}<input type="button" id="exit" value="Выход" onclick="window.location = '/logoff';"><br>
            </span>
        </div>
    </div>
</div>
<div id="loader">
    <img src="<@spring.url "/resources/images/wheel.gif"/>">
    <span style="margin-left:10px;">Загрузка...</span>
</div>
<div id="top_separate"></div>