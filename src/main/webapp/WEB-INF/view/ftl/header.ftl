<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>${pageTitle}</title>

    <link type="text/css" href="<@spring.url "/resources/css/style.css"/>?${resVer}" rel="stylesheet">
    <#if pageCategory == 'lka'>
        <link type="text/css" href="<@spring.url "/resources/css/lkaStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>
    <#if pageCategory == 'mlka'>
        <link type="text/css" href="<@spring.url "/resources/css/mlkaStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>
    <#if pageCategory == 'lkaDmp'>
        <link type="text/css" href="<@spring.url "/resources/css/lkaDmpStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>
    <#if pageCategory == 'administrate'>
        <link type="text/css" href="<@spring.url "/resources/css/administrateStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>
    <#if pageCategory == 'lkaMa'>
        <link type="text/css" href="<@spring.url "/resources/css/lkaMaStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>
    <#if pageCategory == 'nst'>
        <link type="text/css" href="<@spring.url "/resources/css/nstStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>
    <#if pageCategory == 'nka'>
        <link type="text/css" href="<@spring.url "/resources/css/nkaStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>
    <#if pageCategory == 'nkaParam'>
        <link type="text/css" href="<@spring.url "/resources/css/nkaParamStyle.css"/>?${resVer}" rel="stylesheet">
    </#if>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="<@spring.url "/resources/js/main.js"/>?${resVer}" type="text/javascript"></script>
    <#if pageCategory == 'lka'>
        <script src="<@spring.url "/resources/js/lka.js"/>?${resVer}" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'mlka'>
        <script src="<@spring.url "/resources/js/mlka.js"/>?${resVer}" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'lkaDmp'>
        <script src="<@spring.url "/resources/js/lkaDmp.js"/>?${resVer}" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'reports'>
        <script src="<@spring.url "/resources/js/reports.js"/>?${resVer}" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'administrate'>
        <script src="<@spring.url "/resources/js/administrate.js"/>?${resVer}" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'lkaMa'>
        <script src="<@spring.url "/resources/js/lkaMa.js"/>?${resVer}" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'nst'>
        <script src="<@spring.url "/resources/js/nst.js"/>?${resVer}" type="text/javascript"></script>
    </#if>
    <#if pageCategory == 'nka' || pageCategory == 'nkaParam'>
        <script src="<@spring.url "/resources/js/nka.js"/>?${resVer}" type="text/javascript"></script>
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