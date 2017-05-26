<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="header.jsp"%>
<div id="rightColumn">
    <div id="reports" class="content">
        <div id="class_top" class="reports visible">
            <div class="title">Просмотр фотографий</div>

            <c:if test="${fn:contains(sessionScope.user.reportTypeList, 1)}">
                <div class="report_group">
                    <div class="group_title">1. Локальные сети: ДМП</div>
                    <div class="report_link"><a href="/reports/lkaDmp">Просмотр фото</a> </div>
                </div>
            </c:if>

            <c:if test="${fn:contains(sessionScope.user.reportTypeList, 2)}">
                <div class="report_group">
                    <div class="group_title">2. Федеральные сети: фотоотчеты MLKA</div>
                    <div class="report_link" style="display: none"><a href="/">Просмотр фото</a> </div>
                </div>
            </c:if>

            <c:if test="${fn:contains(sessionScope.user.reportTypeList, 3)}">
                <div class="report_group">
                    <div class="group_title">3. Федеральные сети: ДМП</div>
                    <div class="report_link" style="display: none"><a href="/">Просмотр фото</a> </div>
                </div>
            </c:if>

            <c:if test="${fn:contains(sessionScope.user.reportTypeList, 4)}">
                <div class="report_group">
                    <div class="group_title">4. Фото НСТ</div>
                    <div class="report_link" style="display: none"><a href="/">Просмотр фото</a> </div>
                </div>
            </c:if>

            <c:if test="${fn:contains(sessionScope.user.reportTypeList, 5)}">
                <div class="report_group">
                    <div class="group_title">5. Локальные сети</div>
                    <div class="report_link"><a href="/reports/lka">Просмотр фото</a> </div>
                    <c:if test="${sessionScope.user.role >= 2}">
                        <div class="report_link"><a href="/reports/lka_criteria">Критерии по сетям</a> </div>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${sessionScope.user.role >= 2}">
                <div class="report_group">
                    <div class="group_title">Загрузка данных</div>
                    <div class="report_link"><a href="/reports/upload">Загрузить</a> </div>
                </div>
            </c:if>

            <c:if test="${sessionScope.user.role >= 2}">
                <div class="report_group">
                    <div class="group_title">Администрирование</div>
                    <c:if test="${sessionScope.user.role == 4}">
                        <div class="report_link"><a href="/reports/create_user">Добавление пользователя</a> </div>
                    </c:if>
                    <div class="report_link"><a href="/reports/responsib">Распределение ответственных</a> </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@include file="footer.jsp"%>