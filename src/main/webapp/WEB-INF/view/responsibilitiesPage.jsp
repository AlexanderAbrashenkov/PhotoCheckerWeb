<%@ page isELIgnored="false" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="respList" type="java.util.List<com.photochecker.models.Responsibility>" scope="request"></jsp:useBean>
<jsp:useBean id="respUsers" type="java.util.Map<java.lang.Integer, java.util.List<com.photochecker.models.User>>" scope="request"></jsp:useBean>

<div class="report_title">Назначение ответственных верификаторов</div>
<div id="resp_container">
    <div class="resp_title">
        <div class="resp type_name">Вид отчета</div>
        <div class="resp region_name">Регион</div>
        <div class="resp distr_name">Дистрибьютор</div>
        <div class="resp resp_name">Ответственный</div>
    </div>
    <div id="resp_editor_pane">
        <c:forEach items="${respList}" var="respItem">
            <div class="responsib">
                <div class="resp type_name" name="${respItem.reportType}">${respItem.reportName}</div>
                <div class="resp region_name" name="${respItem.regionId}">${respItem.regionName}</div>
                <div class="resp distr_name" name="${respItem.distrId}">${respItem.distrName}"</div>
                <select class="resp resp_name">
                    <option></option>
                    <c:forEach items="${respUsers[respItem.reportType]}" var="userItem">
                        <option>${userItem.userName}</option>
                    </c:forEach>
                </select>
            </div>
        </c:forEach>
    </div>
    <div class="button button_save" id="resp_save">Сохранить изменения</div>
    <div class="button button_cancel" id="resp_cancel">Отменить изменения</div>
</div>

<%@include file="footer.jsp"%>