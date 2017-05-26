<%--
  Created by IntelliJ IDEA.
  User: market6
  Date: 29.03.2017
  Time: 7:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="regionList" type="java.util.List<com.photochecker.model.Region>" scope="request"></jsp:useBean>

<option disabled selected data-value="nothing"> -- выберете регион -- </option>
<c:forEach var="region" items="${regionList}">
    <option data-value="${region.id}">${region.name}</option>
</c:forEach>