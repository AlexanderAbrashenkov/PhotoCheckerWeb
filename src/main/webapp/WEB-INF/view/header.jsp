<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>${pageTitle}</title>

    <link type="text/css" href="../css/style.css" rel="stylesheet">
    <c:if test="${pageCategory.equals('lka')}">
        <link type="text/css" href="../css/lkaStyle.css" rel="stylesheet">
    </c:if>
    <c:if test="${pageCategory.equals('mlka')}">
        <link type="text/css" href="../css/mlkaStyle.css" rel="stylesheet">
    </c:if>
    <c:if test="${pageCategory.equals('lkaDmp')}">
        <link type="text/css" href="../css/lkaDmpStyle.css" rel="stylesheet">
    </c:if>
    <c:if test="${pageCategory.equals('administrate')}">
        <link type="text/css" href="../css/administrateStyle.css" rel="stylesheet">
    </c:if>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="../js/main.js" type="text/javascript"></script>
    <c:if test="${pageCategory.equals('lka')}">
        <script src="../js/lka.js" type="text/javascript"></script>
    </c:if>
    <c:if test="${pageCategory.equals('mlka')}">
        <script src="../js/mlka.js" type="text/javascript"></script>
    </c:if>
    <c:if test="${pageCategory.equals('lkaDmp')}">
        <script src="../js/lkaDmp.js" type="text/javascript"></script>
    </c:if>
    <c:if test="${pageCategory.equals('reports')}">
        <script src="../js/reports.js" type="text/javascript"></script>
    </c:if>
    <c:if test="${pageCategory.equals('administrate')}">
        <script src="../js/administrate.js" type="text/javascript"></script>
    </c:if>
</head>
<body>
<div id="errorBlock">Произошла ошибка! Невозможно подключиться к серверу. Попробуйте позднее!</div>
<div id="savedSuccessfullyBlock">Данные успешно сохранены!</div>
<div id="head_pane">
    <div class="report_title">
        <a href="/" style="border: 0;">Главная</a> /
        <c:if test="true">
            <a href="/reports" style="border: 0;">Отчеты</a>
        </c:if>
    </div>
    <div id="filter_pane">
        <div id="logoff_pane">
            <span id="lbl_user_name">${sessionScope.user.userName}<input type="button" id="exit" value="Выход" onclick="window.location = '/logoff';"><br>
            </span>
        </div>
    </div>
</div>
<div id="loader">
    <img src="../images/wheel.gif">
    <span style="margin-left:10px;">Загрузка...</span>
</div>
<div id="top_separate"></div>