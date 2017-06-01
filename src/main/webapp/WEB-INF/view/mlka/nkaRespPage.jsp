<%@ page isELIgnored="false" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="respList" type="java.util.List<com.photochecker.model.mlka.NkaResp>" scope="request"></jsp:useBean>
<jsp:useBean id="respUsers" type="java.util.List<com.photochecker.model.User>" scope="request"></jsp:useBean>

<div class="report_title">Назначение ответственных верификаторов</div>
<div id="resp_container">
    <div class="resp_title">
        <div class="resp type_name">Сеть</div>
        <div class="resp region_name">Регион</div>
        <div class="resp distr_name">Дистрибьютор</div>
        <div class="resp resp_name">Ответственный</div>
    </div>
    <div id="resp_editor_pane">
        <c:forEach items="${respList}" var="respItem">
            <div class="responsib">
                <div class="resp type_name" name="${respItem.nkaType.id}">${respItem.nkaType.name}</div>
                <div class="resp region_name" name="${respItem.distr.region.id}">${respItem.distr.region.name}</div>
                <div class="resp distr_name" name="${respItem.distr.id}">${respItem.distr.name}</div>
                <select class="resp resp_name">
                    <option name="0"></option>
                    <c:forEach items="${respUsers}" var="userItem">
                        <option name="${userItem.id}" <c:if test="${respItem.user.id == userItem.id}"> selected="selected" </c:if>>
                            ${userItem.userName}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </c:forEach>
    </div>
    <div class="button button_save" id="nka_resp_save">Сохранить изменения</div>
    <div class="button button_cancel" id="resp_cancel">Отменить изменения</div>
</div>

<%@include file="../footer.jsp"%>