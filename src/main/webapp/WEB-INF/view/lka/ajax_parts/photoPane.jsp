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

<jsp:useBean id="photoList" type="java.util.List<com.photochecker.models.PhotoCard>" scope="request"></jsp:useBean>
<c:forEach items="${photoList}" var="photo" varStatus="theCount">

    <fmt:parseDate value="${photo.date}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
    <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="dd.MM.yyyy" />

    <fmt:parseDate value="${photo.dateAdd}" pattern="yyyy-MM-dd" var="parsedDateAdd" type="date" />
    <fmt:formatDate value="${parsedDateAdd}" var="newParsedDateAdd" type="date" pattern="dd.MM.yyyy" />

    <div class="photoBlock">
        <img data-num="${theCount.count}" src="${photo.url}"><br>
        <span class="photoDate">Дата: ${newParsedDate}</span><br>
        <span class="addDate">Дата добавления: ${newParsedDateAdd}</span><br>
        <textarea>${photo.comment}</textarea>
    </div>
</c:forEach>