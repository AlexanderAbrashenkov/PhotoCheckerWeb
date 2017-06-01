<%--
  Created by IntelliJ IDEA.
  User: market6
  Date: 30.03.2017
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<jsp:useBean id="photoList" type="java.util.List<com.photochecker.model.PhotoCard>" scope="request"></jsp:useBean>
<c:forEach items="${photoList}" var="photo" varStatus="theCount">

    <javatime:format value="${photo.date}" pattern="dd.MM.yyyy" var="parsedDate" />

    <javatime:format value="${photo.dateAdd}" pattern="dd.MM.yyyy" var="parsedDateAdd" />

    <div class="photoBlock">
        <img data-num="${theCount.count}" src="${photo.url}"><br>
        <span class="photoDate">Дата: ${parsedDate}</span><br>
        <span class="addDate">Дата добавления: ${parsedDateAdd}</span><br>
        <textarea>${photo.comment}</textarea>
    </div>
</c:forEach>