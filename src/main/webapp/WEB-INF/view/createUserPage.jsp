<%@ page isELIgnored="false" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="reportTypes" type="java.util.List<com.photochecker.model.ReportType>" scope="request"></jsp:useBean>

<div class="report_title">Создание нового пользователя</div>
<div id="crus_container">
    <table cellspacing="0" cellpadding="0">
        <tr>
            <td style="width: 250px;">Логин</td>
            <td>
                <input type="text" name="login" id="login" />
                <div class="errorLabel" id="loginError" style="display: none">Этот логин занят!</div>
                <div class="successLabel" id="loginSuccess" style="display: none">Логин свободен</div>
            </td>
        </tr>
        <tr>
            <td>Пароль</td>
            <td><input type="password" name="password" id="password" /></td>
        </tr>
        <tr>
            <td>Повторите пароль</td>
            <td>
                <input type="password" name="repeat_password" id="repeat_password" />
                <div class="errorLabel" id="passError" style="display: none">Пароли не совпадают!</div>
                <div class="successLabel" id="passSuccess" style="display: none">Пароли совпадают</div>
            </td>
        </tr>
        <tr>
            <td>ФИО</td>
            <td><input type="text" name="user_name" id="user_name" /></td>
        </tr>
        <tr>
            <td>Роль</td>
            <td>
                <select name="role" id="role">
                    <option name="1">1 (Верификатор)</option>
                    <option name="2">2 (Непосредственный руководитель)</option>
                    <option name="3">3 (Руководитель отдела)</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Доступные разделы</td>
            <td>
                <div name="report_types" id="report_types">
                    <c:forEach items="${reportTypes}" var="reportType">
                        <input type="checkbox" name="${reportType.id}">${reportType.id}. ${reportType.name}<br>
                    </c:forEach>
                </div>
            </td>
        </tr>
    </table>
    <div class="button button_save" id="crus_save">Создать пользователя</div>
</div>

<%@include file="footer.jsp"%>